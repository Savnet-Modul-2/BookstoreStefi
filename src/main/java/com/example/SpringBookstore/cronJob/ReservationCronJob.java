package com.example.SpringBookstore.cronJob;

import com.example.SpringBookstore.ReservationStatus;
import com.example.SpringBookstore.entity.Reservation;
import com.example.SpringBookstore.repository.ReservationRepository;
import com.example.SpringBookstore.service.LibrarianService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Configuration
@EnableScheduling
public class ReservationCronJob {
    private final ReservationRepository reservationRepository;
    private final LibrarianService librarianService;

    @Autowired
    public ReservationCronJob(ReservationRepository reservationRepository, LibrarianService librarianService) {
        this.reservationRepository = reservationRepository;
        this.librarianService = librarianService;
    }

    @Transactional
    @Scheduled(cron = "0 0 0 * * *")
    public void updateReservationStatus() {
        List<Reservation> reservationsToBeDelayed = reservationRepository
                .findAllReservationsToBeDelayed(LocalDate.now());
        List<Reservation> reservationsToBeCancelled = reservationRepository
                .findAllReservationsToBeCancelled(LocalDate.now());

        if (!reservationsToBeDelayed.isEmpty()) {
            reservationsToBeDelayed.forEach(reservation -> reservation.setStatus(ReservationStatus.DELAYED));
            reservationRepository.saveAll(reservationsToBeDelayed);

            librarianService.sendDelayedReservationsEmail(reservationsToBeDelayed);
        }

        if (!reservationsToBeCancelled.isEmpty()) {
            reservationsToBeCancelled.forEach(reservation -> reservation.setStatus(ReservationStatus.FINISHED));
            reservationRepository.saveAll(reservationsToBeCancelled);
        }
    }
}
