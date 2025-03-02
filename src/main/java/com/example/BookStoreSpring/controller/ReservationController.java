package com.example.BookStoreSpring.controller;

import com.example.BookStoreSpring.entities.Book;
import com.example.BookStoreSpring.entities.Reservation;
import com.example.BookStoreSpring.mapper.BookMapper;
import com.example.BookStoreSpring.mapper.ReservationMapper;
import com.example.BookStoreSpring.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping(path = "/reservations")
public class ReservationController {
    private final ReservationService reservationService;

    @Autowired
    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping
    public ResponseEntity<?> searchBooks(@RequestParam(required = false) String title, @RequestParam(required = false) String author, @RequestParam Integer pageNumber, @RequestParam Integer numberOfElements) {
        Page<Book> foundBooks = reservationService.searchBooks(title, author, pageNumber, numberOfElements);

        return ResponseEntity.ok(foundBooks.stream()
                .map(BookMapper::book2BookDTO)
                .toList());
    }

    @PostMapping(path = "/{userID}/{bookID}")
    public ResponseEntity<?> reserveBook(@PathVariable(name = "userID") Long userID, @PathVariable(name = "bookID") Long bookID, @RequestParam LocalDate startDate, @RequestParam LocalDate endDate) {
        Reservation reservation = reservationService.reserveBook(userID, bookID, startDate, endDate);
        return ResponseEntity.ok(ReservationMapper.reservation2ReservationDTO(reservation));
    }
}
