package com.example.SpringBookstore.repository;

import com.example.SpringBookstore.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    @Query(value = """
            SELECT r.* FROM reservations r
            WHERE r.STATUS = 'IN_PROGRESS'
            AND r.END_DATE < :currentDate
            """, nativeQuery = true)
    List<Reservation> findAllReservationsToBeDelayed(@Param(value = "currentDate") LocalDate currentDate);

    @Query(value = """
            SELECT r.* FROM reservations r
            WHERE r.STATUS = 'PENDING'
            AND r.START_DATE < :currentDate
            """, nativeQuery = true)
    List<Reservation> findAllReservationsToBeCancelled(@Param(value = "currentDate") LocalDate currentDate);
}
