package com.example.BookStoreSpring.repositories;

import com.example.BookStoreSpring.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository()
public interface BookRepository extends JpaRepository<Book, Long> {
}
