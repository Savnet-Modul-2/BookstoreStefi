package com.example.SpringBookstore.repository;

import com.example.SpringBookstore.entity.Copy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface CopyRepository extends JpaRepository<Copy, Long> {
    Page<Copy> findByBookId(Long bookId, Pageable pageable);

    @Query(value = """
            SELECT c.* FROM copies c
            WHERE c.BOOK_ID = :bookId
            AND c.ID NOT IN (
                SELECT r.COPY_ID FROM reservations r
                WHERE r.COPY_ID = c.ID
                AND NOT (:startDate > r.END_DATE OR :endDate < r.START_DATE OR r.STATUS = 'FINISHED')
            )
            LIMIT 1
            """, nativeQuery = true)
    Optional<Copy> findFirstAvailableCopy(@Param(value = "bookId") Long bookId,
                                          @Param(value = "startDate") LocalDate startDate,
                                          @Param(value = "endDate") LocalDate endDate);
}
