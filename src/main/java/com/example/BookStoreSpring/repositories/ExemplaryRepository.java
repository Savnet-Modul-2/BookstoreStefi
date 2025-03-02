package com.example.BookStoreSpring.repositories;

import com.example.BookStoreSpring.entities.Exemplary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ExemplaryRepository extends JpaRepository<Exemplary, Long> {
    Page<Exemplary> findByBookId(Long bookID, Pageable pageable);

    //Alternative to @Query
    /*
    Optional<Exemplary> findFirstByBookId(Long bookID);
    */

    //Alternative to Native Query - unfortunately, it returns a list and there is no way to set a limit for it;
    /*
    @Query(value = """
        SELECT aExemplary FROM exemplary aExemplary
        LEFT JOIN aExemplary.reservation aReservation
        WHERE aExemplary.book.id = :bookID
        AND
        (aReservation IS NULL OR aReservation.status = com.example.SpringBookstore.Status.FINISHED)
        ORDER BY aExemplary.id ASC
        """)
    Optional<Exemplary> reserveExemplary(@Param("bookID") Long bookID);
    */

    //Native Query - it causes more trouble than it's worth in case you also want to specify a cascade type for the Exemplary field inside the Reservation class;
    @Query(value = """
            SELECT aExemplary.* FROM exemplars aExemplary
            LEFT JOIN reservations aReservation ON aExemplary.id = aReservation.exemplary_id
            WHERE aExemplary.book_id = :bookID
            AND
            (aReservation.id IS NULL OR aReservation.status = 'FINISHED')
            ORDER BY aExemplary.id ASC
            LIMIT 1
            """, nativeQuery = true)
    Optional<Exemplary> reserveExemplary(@Param("bookID") Long bookID);
}
