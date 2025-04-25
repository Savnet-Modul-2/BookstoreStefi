package com.example.SpringBookstore.controller;

import com.example.SpringBookstore.dto.ReservationDTO;
import com.example.SpringBookstore.dto.ReservationFiltersDTO;
import com.example.SpringBookstore.dto.validation.information.ValidationOrder;
import com.example.SpringBookstore.entity.Book;
import com.example.SpringBookstore.entity.Reservation;
import com.example.SpringBookstore.mapper.BookMapper;
import com.example.SpringBookstore.mapper.ReservationMapper;
import com.example.SpringBookstore.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/reservations")
public class ReservationController {
    private final ReservationService reservationService;

    @Autowired
    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping
    public ResponseEntity<?> create(@Validated(value = ValidationOrder.class) @RequestBody ReservationDTO reservationDTO) {
        Reservation reservationToCreate = ReservationMapper.reservationDTO2Reservation(reservationDTO);
        Reservation createdReservation = reservationService.create(reservationToCreate);

        return ResponseEntity.ok().body(ReservationMapper.reservation2ReservationDTO(createdReservation));
    }

    @GetMapping(path = "/{reservationId}")
    public ResponseEntity<?> findById(@PathVariable(name = "reservationId") Long reservationId) {
        Reservation foundReservation = reservationService.findById(reservationId);
        return ResponseEntity.ok().body(ReservationMapper.reservation2ReservationDTO(foundReservation));
    }

    @GetMapping
    public ResponseEntity<?> findAll(@RequestParam(required = false) Integer pageSize) {
        Page<Reservation> foundReservations = reservationService.findAll(pageSize);
        return ResponseEntity.ok().body(foundReservations.stream()
                .map(ReservationMapper::reservation2ReservationDTO)
                .toList());
    }

    @PutMapping(path = "/{reservationId}")
    public ResponseEntity<?> update(@PathVariable(name = "reservationId") Long reservationId,
                                    @Validated(value = ValidationOrder.class) @RequestBody ReservationDTO reservationDTO) {
        Reservation updatedReservation = reservationService.update(reservationId, reservationDTO);
        return ResponseEntity.ok().body(ReservationMapper.reservation2ReservationDTO(updatedReservation));
    }

    @DeleteMapping(path = "/{reservationId}")
    public ResponseEntity<?> delete(@PathVariable(name = "reservationId") Long reservationId) {
        reservationService.delete(reservationId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(path = "/search")
    public ResponseEntity<?> searchBooks(@RequestParam(required = false) String title,
                                         @RequestParam(required = false) String author,
                                         @RequestParam(required = false) Integer pageNumber,
                                         @RequestParam(required = false) Integer pageSize) {
        Page<Book> foundBooks = reservationService.searchBooks(title, author, pageNumber, pageSize);
        return ResponseEntity.ok(foundBooks.stream()
                .map(BookMapper::book2BookDTO)
                .toList());
    }

    @PostMapping(path = "/{userId}/{bookId}")
    public ResponseEntity<?> reserveCopy(@PathVariable(name = "userId") Long userId,
                                         @PathVariable(name = "bookId") Long bookId,
                                         @Validated(value = ValidationOrder.class) @RequestBody ReservationDTO reservationDTO) {
        Reservation createdReservation = reservationService.reserveCopy(userId,
                bookId,
                reservationDTO.getStartDate(),
                reservationDTO.getEndDate());
        return ResponseEntity.ok(ReservationMapper.reservation2ReservationDTO(createdReservation));
    }

    @PutMapping(path = "/{librarianId}/{reservationId}")
    public ResponseEntity<?> updateStatus(@PathVariable(name = "librarianId") Long librarianId,
                                          @PathVariable(name = "reservationId") Long reservationId) {
        Reservation updatedReservation = reservationService.updateStatus(librarianId, reservationId);
        return ResponseEntity.ok(ReservationMapper.reservation2ReservationDTO(updatedReservation));
    }

    @GetMapping(path = "/user/{userId}")
    public ResponseEntity<?> findReservationsFromUserByStatus(@PathVariable(value = "userId") Long userId,
                                                              @RequestBody ReservationFiltersDTO reservationFiltersDTO,
                                                              @RequestParam(required = false, defaultValue = "START_DATE") String sortCriteria,
                                                              @RequestParam(required = false, defaultValue = "ASC") String sortDirection,
                                                              @RequestParam(required = false) Integer pageNumber,
                                                              @RequestParam(required = false) Integer pageSize) {
        Page<Reservation> foundReservations = reservationService.findReservationsFromUserByStatus(userId, reservationFiltersDTO, sortCriteria, sortDirection, pageNumber, pageSize);

        return ResponseEntity.ok(foundReservations.stream()
                .map(ReservationMapper::reservation2ReservationDTO)
                .toList());
    }

    @GetMapping(path = "/library/{libraryId}")
    public ResponseEntity<?> findReservationsFromLibraryForPeriod(@PathVariable(value = "libraryId") Long libraryId,
                                                                  @RequestBody ReservationFiltersDTO reservationFiltersDTO,
                                                                  @RequestParam(required = false, defaultValue = "START_DATE") String sortCriteria,
                                                                  @RequestParam(required = false, defaultValue = "ASC") String sortDirection,
                                                                  @RequestParam(required = false) Integer pageNumber,
                                                                  @RequestParam(required = false) Integer pageSize) {
        Page<Reservation> foundReservations = reservationService.findReservationsFromLibraryForPeriod(libraryId, reservationFiltersDTO, sortCriteria, sortDirection, pageNumber, pageSize);

        return ResponseEntity.ok(foundReservations.stream()
                .map(ReservationMapper::reservation2ReservationDTO)
                .toList());
    }
}
