package com.example.SpringBookstore.repository;

import com.example.SpringBookstore.entity.Library;
import com.example.SpringBookstore.entity.Reservation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface LibraryRepository extends JpaRepository<Library, Long> {
    @Query(value = """
            SELECT r.* FROM reservations r
            JOIN copies c ON r.COPY_ID = c.ID
            JOIN books b ON c.BOOK_ID = b.ID
            JOIN libraries l ON b.LIBRARY_ID = l.ID
            WHERE l.ID = :libraryId
            AND r.START_DATE >= :startDate
            AND r.END_DATE <= :endDate
            AND r.STATUS IN :reservationStatusFilters
            """,
            countQuery = """
                    SELECT COUNT(*) FROM reservations r
                    JOIN copies c ON r.COPY_ID = c.ID
                    JOIN books b ON c.BOOK_ID = b.ID
                    JOIN libraries l ON b.LIBRARY_ID = l.ID
                    WHERE l.ID = :libraryId
                    AND r.START_DATE >= :startDate
                    AND r.END_DATE <= :endDate
                    AND r.STATUS IN :reservationStatusFilters
                    """,
            nativeQuery = true)
    Page<Reservation> findReservationByLibraryIdForPeriod(@Param(value = "libraryId") Long libraryId, LocalDate startDate, LocalDate endDate, List<String> reservationStatusFilters, Pageable pageable);

    /*
    @Query(value = """
            SELECT l.* FROM libraries l
            WHERE (
                SELECT u.* FROM users u
            )
            """, nativeQuery = true)
   */
    @Query(value = """
            SELECT l.* FROM libraries l
            """, nativeQuery = true)
    Page<Library> findFavouriteLibraries(@Param(value = "userId") Long userId, Pageable pageable);
}
