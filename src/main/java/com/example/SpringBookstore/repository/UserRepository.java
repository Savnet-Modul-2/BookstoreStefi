package com.example.SpringBookstore.repository;

import com.example.SpringBookstore.entity.Reservation;
import com.example.SpringBookstore.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmailAddress(String emailAddress);

    @Query(value = """
            SELECT r.* FROM reservations r
            WHERE r.USER_ID = :userId
            AND (r.STATUS = NULL OR r.STATUS IN :reservationStatusFilters)
            """,
            countQuery = """
                    SELECT COUNT(*) FROM reservations r
                    WHERE r.USER_ID = :userId
                    AND (r.STATUS = NULL OR r.STATUS IN :reservationStatusFilters)
                    """,
            nativeQuery = true)
    Page<Reservation> findReservationFromUserByStatus(@Param(value = "userId") Long userId, @Param(value = "reservationStatusFilters") List<String> reservationStatusFilters, Pageable pageable);
}
