package com.example.BookStoreSpring.service;

import com.example.BookStoreSpring.entities.Librarian;
import com.example.BookStoreSpring.entities.Library;
import com.example.BookStoreSpring.repositories.LibrarianRepository;
import com.example.BookStoreSpring.repositories.LibraryRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Random;

@Service()
public class LibrarianService {
    @Autowired()
    private LibrarianRepository librarianRepository;
    @Autowired()
    private LibraryRepository libraryRepository;
    @Autowired()
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String sender;

    @Transactional()
    public Librarian create(Librarian librarianToCreate) {
        librarianToCreate.setPassword(DigestUtils.md5DigestAsHex(librarianToCreate.getPassword().getBytes(StandardCharsets.UTF_8)));

        Library library = new Library();

        library.setName(librarianToCreate.getLibrary().getName());
        library.setAddress(librarianToCreate.getLibrary().getAddress());
        library.setPhoneNumber(librarianToCreate.getLibrary().getPhoneNumber());
        library.setLibrarian(librarianToCreate);

        if (librarianToCreate.getLibrary().getBooks() != null && !librarianToCreate.getLibrary().getBooks().isEmpty()) {
            library.setBooks(librarianToCreate.getLibrary().getBooks());
        }

        libraryRepository.save(library);

        librarianToCreate.setLibrary(library);

        return librarianRepository.save(librarianToCreate);
    }

    public Librarian checkLibrarian(Long librarianID) {
        Librarian librarian = librarianRepository.findById(librarianID).orElseThrow(() -> new EntityNotFoundException("Librarian with ID " + librarianID + " not found."));

        if (librarian.getEmail() == null || librarian.getEmail().isEmpty()) {
            throw new IllegalArgumentException("Librarian with ID " + librarianID + " does not have an email address.");
        }

        return librarian;
    }

    public void send(String emailAddress, String subject, String text) {
        SimpleMailMessage email = new SimpleMailMessage();

        email.setFrom(sender);
        email.setTo(emailAddress);
        email.setSubject(subject);
        email.setText(text);

        javaMailSender.send(email);
    }

    public String generateVerificationCode() {
        Random random = new Random();
        return String.valueOf(random.nextInt(100000, 999999));
    }

    public void sendVerificationCode(Librarian librarian) {
        if (!librarian.getVerifiedAccount()) {
            librarian.setVerificationCode(generateVerificationCode());
            librarian.setVerificationCodeGenerationTime(LocalDateTime.now());
            librarianRepository.save(librarian);

            send(librarian.getEmail(), "Librarian Verification Email", "Your verification code is: " + librarian.getVerificationCode() + ".\nThis verification code will expire in 5 minutes.");
        }
    }

    public String checkVerificationCode(Librarian librarian, String code) {
        LocalDateTime currentTime = LocalDateTime.now();
        Duration elapsedTime = Duration.between(librarian.getVerificationCodeGenerationTime(), currentTime);

        if (elapsedTime.toMinutes() > 5) {
            librarian.setVerificationCode(null);

            librarianRepository.save(librarian);

            return "\nVerification code expired. Request a new verification code.";
        } else if (!librarian.getVerificationCode().equals(code)) {
            return "\nLibrarian account verification unsuccessful. Invalid code provided.";
        } else {
            librarian.setVerifiedAccount(true);
            librarian.setVerificationCode(null);
            librarian.setVerificationCodeGenerationTime(null);

            librarianRepository.save(librarian);

            return "\nLibrarian account verification successful.";
        }
    }

    public Boolean login(Librarian librarian, String emailAddress, String password) {
        String encryptedPassword = DigestUtils.md5DigestAsHex(password.getBytes(StandardCharsets.UTF_8));

        if (!librarian.getEmail().equals(emailAddress) || !librarian.getPassword().equals(encryptedPassword))
            return false;

        librarian.setLoggedIn(true);
        librarianRepository.save(librarian);

        return true;
    }
}
