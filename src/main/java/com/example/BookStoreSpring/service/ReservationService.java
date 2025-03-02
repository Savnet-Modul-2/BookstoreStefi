package com.example.BookStoreSpring.service;

import com.example.BookStoreSpring.entities.Book;
import com.example.BookStoreSpring.entities.Exemplary;
import com.example.BookStoreSpring.entities.Reservation;
import com.example.BookStoreSpring.entities.User;
import com.example.BookStoreSpring.repositories.BookRepository;
import com.example.BookStoreSpring.repositories.ExemplaryRepository;
import com.example.BookStoreSpring.repositories.ReservationRepository;
import com.example.BookStoreSpring.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.InputMismatchException;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    private final ExemplaryRepository exemplaryRepository;

    @Autowired
    public ReservationService(ReservationRepository reservationRepository, UserRepository userRepository, BookRepository bookRepository, ExemplaryRepository exemplaryRepository) {
        this.reservationRepository = reservationRepository;
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
        this.exemplaryRepository = exemplaryRepository;
    }

    public Page<Book> searchBooks(String title, String author, Integer pageNumber, Integer numberOfElements) {
        if (pageNumber != null && numberOfElements != null) {
            Pageable pageable = PageRequest.of(pageNumber, numberOfElements);
            return bookRepository.searchBooks(title, author, pageable);
        }

        return bookRepository.searchBooks(title, author, Pageable.unpaged());
    }

    public Reservation reserveBook(Long userID, Long bookID, LocalDate startDate, LocalDate endDate) {
        if (startDate.isAfter(endDate) || startDate.isBefore(LocalDate.now()) || endDate.isBefore(LocalDate.now())) {
            throw new InputMismatchException("Reservation unsuccessful. Invalid reservation dates.");
        }

        User user = userRepository.findById(userID)
                .orElseThrow(() -> new EntityNotFoundException("User with ID " + userID + " not found."));

        bookRepository.findById(bookID)
                .orElseThrow(() -> new EntityNotFoundException("Book with ID " + bookID + " not found."));

        Exemplary exemplary = exemplaryRepository.reserveExemplary(bookID)
                .orElseThrow(() -> new EntityNotFoundException("No exemplars of book with ID " + bookID + " available."));

        Reservation reservation = new Reservation();

        reservation.setStartDate(startDate);
        reservation.setEndDate(endDate);
        reservation.setExemplary(exemplary);

        user.addReservation(reservation);
        exemplary.setReservation(reservation);

        reservationRepository.save(reservation);
        exemplaryRepository.save(exemplary);
        userRepository.save(user);

        return reservation;
    }
}
