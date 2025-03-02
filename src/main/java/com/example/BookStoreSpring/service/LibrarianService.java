package com.example.BookStoreSpring.service;

import com.example.BookStoreSpring.entities.Librarian;
import com.example.BookStoreSpring.entities.Library;
import com.example.BookStoreSpring.repositories.LibrarianRepository;
import com.example.BookStoreSpring.repositories.LibraryRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.InputMismatchException;
import java.util.List;

@Service
public class LibrarianService extends EmailService {
    private final LibrarianRepository librarianRepository;
    private final LibraryRepository libraryRepository;

    @Autowired
    public LibrarianService(JavaMailSender javaMailSender, LibrarianRepository librarianRepository, LibraryRepository libraryRepository) {
        super(javaMailSender);
        this.librarianRepository = librarianRepository;
        this.libraryRepository = libraryRepository;
    }

    @Transactional
    public Librarian create(Librarian librarianToCreate) {
        if (librarianToCreate.getID() != null) {
            throw new RuntimeException("Cannot provide an ID when creating a new librarian.");
        }

        librarianToCreate.setPassword(DigestUtils.md5DigestAsHex(librarianToCreate.getPassword().getBytes(StandardCharsets.UTF_8)));

        Library library = new Library();

        library.setName(librarianToCreate.getLibrary().getName());
        library.setAddress(librarianToCreate.getLibrary().getAddress());
        library.setPhoneNumber(librarianToCreate.getLibrary().getPhoneNumber());
        library.setLibrarian(librarianToCreate);

        if (librarianToCreate.getLibrary().getBooks() != null) {
            library.setBooks(librarianToCreate.getLibrary().getBooks());
        }

        libraryRepository.save(library);

        librarianToCreate.setLibrary(library);

        return librarianRepository.save(librarianToCreate);
    }

    public Librarian findByID(Long librarianID) {
        return librarianRepository.findById(librarianID)
                .orElseThrow(() -> new EntityNotFoundException("Librarian with ID " + librarianID + " not found."));
    }

    public List<Librarian> findAll() {
        return librarianRepository.findAll();
    }

    public void delete(Long librarianID) {
        if (librarianRepository.existsById(librarianID)) {
            librarianRepository.deleteById(librarianID);
        }
    }

    public Librarian checkEmail(Long librarianID) {
        Librarian librarian = librarianRepository.findById(librarianID)
                .orElseThrow(() -> new EntityNotFoundException("Librarian with ID " + librarianID + " not found."));

        if (librarian.getEmail() == null || librarian.getEmail().isEmpty()) {
            throw new IllegalArgumentException("Librarian with ID " + librarianID + " does not have an email address.");
        }

        return librarian;
    }

    public void sendVerificationCode(Librarian librarian) {
        if (!librarian.getVerifiedAccount()) {
            librarian.setVerificationCode(generateVerificationCode());
            librarian.setVerificationCodeGenerationTime(LocalDateTime.now());

            librarianRepository.save(librarian);

            sendEmail(librarian.getEmail(), "Librarian Verification Email", "Your verification code is: " + librarian.getVerificationCode() + ".\nThis verification code will expire in 5 minutes.");
        }
    }

    public Librarian checkVerificationCode(Long librarianID, String code) {
        Librarian librarian = findByID(librarianID);

        LocalDateTime currentTime = LocalDateTime.now();
        Duration elapsedTime = Duration.between(librarian.getVerificationCodeGenerationTime(), currentTime);

        if (elapsedTime.toMinutes() > 5) {
            librarian.setVerificationCode(null);

            librarianRepository.save(librarian);

            throw new RuntimeException("Verification code expired. Request a new verification code.");
        } else if (!librarian.getVerificationCode().equals(code)) {
            throw new RuntimeException("Librarian account verification unsuccessful. Invalid code provided.");
        }

        librarian.setVerifiedAccount(true);
        librarian.setVerificationCode(null);
        librarian.setVerificationCodeGenerationTime(null);

        librarianRepository.save(librarian);

        return librarian;
    }

    public Librarian login(String emailAddress, String password) {
        Librarian librarian = librarianRepository.findByEmail(emailAddress)
                .orElseThrow(() -> new EntityNotFoundException("Librarian with email address " + emailAddress + " not found."));

        String encryptedPassword = DigestUtils.md5DigestAsHex(password.getBytes(StandardCharsets.UTF_8));

        if (!librarian.getEmail().equals(emailAddress) || !librarian.getPassword().equals(encryptedPassword)) {
            throw new InputMismatchException("Login unsuccessful. Invalid email address or password.");
        }

        librarianRepository.save(librarian);

        return librarian;
    }
}
