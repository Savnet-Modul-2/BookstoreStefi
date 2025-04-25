package com.example.SpringBookstore.service;

import com.example.SpringBookstore.ReservationStatus;
import com.example.SpringBookstore.dto.ReservationDTO;
import com.example.SpringBookstore.dto.ReservationFiltersDTO;
import com.example.SpringBookstore.entity.*;
import com.example.SpringBookstore.repository.*;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final LibrarianRepository librarianRepository;
    private final LibraryRepository libraryRepository;
    private final BookRepository bookRepository;
    private final CopyRepository copyRepository;
    private final UserRepository userRepository;

    @Autowired
    public ReservationService(ReservationRepository reservationRepository,
                              LibrarianRepository librarianRepository,
                              LibraryRepository libraryRepository,
                              BookRepository bookRepository,
                              CopyRepository copyRepository,
                              UserRepository userRepository) {
        this.reservationRepository = reservationRepository;
        this.librarianRepository = librarianRepository;
        this.libraryRepository = libraryRepository;
        this.bookRepository = bookRepository;
        this.copyRepository = copyRepository;
        this.userRepository = userRepository;
    }

    public Reservation create(Reservation reservationToCreate) {
        if (reservationToCreate.getId() != null) {
            throw new RuntimeException("Cannot provide an ID when creating a new reservation.");
        }

        return reservationRepository.save(reservationToCreate);
    }

    public Reservation findById(Long reservationId) {
        return reservationRepository.findById(reservationId)
                .orElseThrow(() -> new EntityNotFoundException("Reservation with ID " + reservationId + " not found."));
    }

    public Page<Reservation> findAll(Integer pageSize) {
        Pageable pageable = pageSize != null ? PageRequest.of(0, pageSize) : Pageable.unpaged();
        return reservationRepository.findAll(pageable);
    }

    public Reservation update(Long reservationId, ReservationDTO reservationDTO) {
        Reservation reservationToUpdate = findById(reservationId);

        reservationToUpdate.setStartDate(reservationDTO.getStartDate());
        reservationToUpdate.setEndDate(reservationDTO.getEndDate());

        return reservationRepository.save(reservationToUpdate);
    }

    public void delete(Long reservationId) {
        if (!reservationRepository.existsById(reservationId)) {
            throw new EntityNotFoundException("Cannot delete reservation. Reservation with ID " + reservationId + " not found.");
        }

        reservationRepository.deleteById(reservationId);
    }

    public Page<Book> searchBooks(String title, String author, Integer pageNumber, Integer pageSize) {
        Pageable pageable = (pageNumber != null && pageSize != null) ? PageRequest.of(pageNumber, pageSize) : Pageable.unpaged();
        return bookRepository.findByTitleAndAuthor(title, author, pageable);
    }

    @Transactional
    public Reservation reserveCopy(Long userId, Long bookId, LocalDate startDate, LocalDate endDate) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User with ID " + userId + " not found."));

        Copy copy = copyRepository.findFirstAvailableCopy(bookId, startDate, endDate)
                .orElseThrow(() -> new EntityNotFoundException("No copies available for reservations."));

        Reservation reservation = new Reservation();

        reservation.setStartDate(startDate);
        reservation.setEndDate(endDate);
        reservation.setStatus(ReservationStatus.PENDING);

        user.addReservation(reservation);

        copy.addReservation(reservation);
        copy.setUpdateTime(LocalDateTime.now());

        userRepository.save(user);
        copyRepository.save(copy);

        return reservationRepository.save(reservation);
    }

    public Reservation updateStatus(Long librarianId, Long reservationId) {
        Librarian librarian = librarianRepository.findById(librarianId)
                .orElseThrow(() -> new EntityNotFoundException("Librarian with ID " + librarianId + " not found."));

        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new EntityNotFoundException("Reservation with ID " + reservationId + " not found."));

        if (reservation.getCopy().getBook().getLibrary().getLibrarian().equals(librarian)) {
            if (reservation.getStatus().isNextStatusValid(ReservationStatus.IN_PROGRESS)) {
                reservation.setStatus(ReservationStatus.IN_PROGRESS);
            } else if (reservation.getStatus().isNextStatusValid(ReservationStatus.FINISHED)) {
                reservation.setStatus(ReservationStatus.FINISHED);
            }
        }

        return reservationRepository.save(reservation);
    }

    public Page<Reservation> findReservationsFromUserByStatus(Long userId, ReservationFiltersDTO reservationFiltersDTO, String sortCriteria, String sortDirection, Integer pageNumber, Integer pageSize) {
        userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User with ID " + userId + " not found."));

        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortCriteria);
        Pageable pageable = (pageNumber != null && pageSize != null) ? PageRequest.of(pageNumber, pageSize, sort) : Pageable.unpaged();

        if (reservationFiltersDTO.reservationStatusFilters() != null && !reservationFiltersDTO.reservationStatusFilters().isEmpty()) {
            List<String> reservationStatusFilterStrings = reservationFiltersDTO.reservationStatusFilters().stream()
                    .map(Enum::name)
                    .toList();

            return userRepository.findReservationFromUserByStatus(userId, reservationStatusFilterStrings, pageable);
        }

        return userRepository.findReservationFromUserByStatus(userId, null, pageable);
    }

    public Page<Reservation> findReservationsFromLibraryForPeriod(Long libraryId, ReservationFiltersDTO reservationFiltersDTO, String sortCriteria, String sortDirection, Integer pageNumber, Integer pageSize) {
        libraryRepository.findById(libraryId)
                .orElseThrow(() -> new EntityNotFoundException("Library with ID" + libraryId + " not found."));

        List<String> reservationStatusFilterStrings = reservationFiltersDTO.reservationStatusFilters().stream()
                .map(Enum::name)
                .toList();

        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortCriteria);
        Pageable pageable = (pageNumber != null && pageSize != null) ? PageRequest.of(pageNumber, pageSize, sort) : Pageable.unpaged();

        return libraryRepository.findReservationByLibraryIdForPeriod(libraryId, reservationFiltersDTO.startDate(), reservationFiltersDTO.endDate(), reservationStatusFilterStrings, pageable);
    }
}
