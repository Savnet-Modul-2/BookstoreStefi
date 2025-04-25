package com.example.SpringBookstore.repository;

import com.example.SpringBookstore.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    @Query(value = """
            SELECT b.* FROM books b
            WHERE
            (:title IS NOT NULL OR :author IS NOT NULL)
            AND
            (:title IS NULL OR LOWER(b.TITLE) LIKE LOWER(CONCAT('%', :title, '%')))
            AND
            (:author IS NULL OR LOWER(b.AUTHOR) LIKE LOWER(CONCAT('%', :author, '%')))
            """, nativeQuery = true)
    Page<Book> findByTitleAndAuthor(@Param(value = "title") String title, @Param(value = "author") String author, Pageable pageable);
}
