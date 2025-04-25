package com.example.SpringBookstore.service;

import com.example.SpringBookstore.dto.LibrarianDTO;
import com.example.SpringBookstore.entity.Librarian;
import com.example.SpringBookstore.entity.Library;
import com.example.SpringBookstore.entity.Reservation;
import com.example.SpringBookstore.exceptionHandling.exception.BadRequestException;
import com.example.SpringBookstore.repository.LibrarianRepository;
import com.example.SpringBookstore.repository.LibraryRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class LibrarianService {
    private final LibrarianRepository librarianRepository;
    private final LibraryRepository libraryRepository;
    private final EmailService emailService;

    @Autowired
    public LibrarianService(LibrarianRepository librarianRepository, LibraryRepository libraryRepository, EmailService emailService) {
        this.librarianRepository = librarianRepository;
        this.libraryRepository = libraryRepository;
        this.emailService = emailService;
    }

    @Transactional
    public Librarian create(Librarian librarianToCreate) {
        if (librarianToCreate.getId() != null) {
            throw new RuntimeException("Cannot provide an ID when creating a new librarian.");
        }

        librarianToCreate.setPassword(DigestUtils.md5DigestAsHex(librarianToCreate.getPassword().getBytes(StandardCharsets.UTF_8)));

        Library library = librarianToCreate.getLibrary();
        library.setLibrarian(librarianToCreate);

        libraryRepository.save(library);

        return librarianRepository.save(librarianToCreate);
    }

    public Librarian findById(Long librarianId) {
        return librarianRepository.findById(librarianId)
                .orElseThrow(() -> new EntityNotFoundException("Librarian with ID " + librarianId + " not found."));
    }

    public Page<Librarian> findAll(Integer pageSize) {
        Pageable pageable = pageSize != null ? PageRequest.of(0, pageSize) : Pageable.unpaged();
        return librarianRepository.findAll(pageable);
    }

    public Librarian update(Long librarianId, LibrarianDTO librarianUpdate) {
        Librarian userToUpdate = findById(librarianId);

        userToUpdate.setFirstName(librarianUpdate.getFirstName());
        userToUpdate.setLastName(librarianUpdate.getLastName());
        userToUpdate.setEmailAddress(librarianUpdate.getEmailAddress());
        userToUpdate.setPassword(librarianUpdate.getPassword());

        return librarianRepository.save(userToUpdate);
    }

    public void delete(Long librarianId) {
        if (!librarianRepository.existsById(librarianId)) {
            throw new EntityNotFoundException("Cannot delete librarian. Librarian with ID " + librarianId + " not found.");
        }

        librarianRepository.deleteById(librarianId);
    }

    public void sendEmail(Long librarianId) {
        Librarian librarian = findById(librarianId);
        emailService.sendEmail(librarian.getEmailAddress(), "Bookstore Email", "This is an email for " + librarian.getFirstName() + " " + librarian.getLastName() + ".");
    }

    public void sendVerificationEmail(Librarian librarian) {
        if (!librarian.getVerifiedAccount()) {
            String verificationCode = emailService.generateVerificationCode();

            librarian.setVerificationCode(verificationCode);
            librarian.setVerificationCodeGenerationTime(LocalDateTime.now());

            librarianRepository.save(librarian);

            emailService.sendVerificationEmail(librarian.getEmailAddress(), verificationCode, emailService.getMaximumVerificationTime());
        }
    }

    public void resendVerificationEmail(Long librarianId) {
        Librarian librarian = findById(librarianId);

        long remainingTime = emailService.getMaximumVerificationTime() - Duration.between(librarian.getVerificationCodeGenerationTime(), LocalDateTime.now()).toMinutes();

        if (remainingTime > 1) {
            emailService.sendVerificationEmail(librarian.getEmailAddress(), librarian.getVerificationCode(), remainingTime);
        } else {
            sendVerificationEmail(librarian);
        }
    }

    public Librarian verifyAccount(Long librarianId, String verificationCode) {
        Librarian librarian = findById(librarianId);

        if (Duration.between(librarian.getVerificationCodeGenerationTime(), LocalDateTime.now()).toMinutes() > 5) {
            throw new BadRequestException("Verification code expired. Request a new verification code.");
        } else if (!librarian.getVerificationCode().equals(verificationCode)) {
            throw new BadRequestException("User account verification unsuccessful. Invalid verification code.");
        } else {
            librarian.setVerificationCodeGenerationTime(null);
            librarian.setVerificationCode(null);

            librarian.setVerifiedAccount(true);

            librarianRepository.save(librarian);
        }

        return librarian;
    }

    public Librarian login(String emailAddress, String password) {
        Librarian librarian = librarianRepository.findByEmailAddress(emailAddress)
                .orElseThrow(() -> new EntityNotFoundException("Librarian with email address " + emailAddress + " not found."));

        String hashedPassword = DigestUtils.md5DigestAsHex(password.getBytes(StandardCharsets.UTF_8));

        if (!librarian.getEmailAddress().equals(emailAddress) || !hashedPassword.equals(librarian.getPassword())) {
            throw new BadRequestException("Login unsuccessful. Invalid username or password.");
        }

        return librarian;
    }

    public void sendDelayedReservationsEmail(List<Reservation> delayedReservations) {
        delayedReservations.forEach(reservation -> {
            String librarianEmailAddress = reservation.getCopy().getBook().getLibrary().getLibrarian().getEmailAddress();
            String userEmailAddress = reservation.getUser().getEmailAddress();
            String userFullName = reservation.getUser().getFirstName() + " " + reservation.getUser().getLastName();

            String text = "Reservation with ID " + reservation.getId() + " has been delayed." +
                    "\nPlease see the user details below:" +
                    "\nUser full name: " +
                    userFullName +
                    "\nUser email address: " +
                    userEmailAddress;

            emailService.sendEmail(librarianEmailAddress, "Delayed Reservation", text);
        });
    }
}
