package com.example.BookStoreSpring.repositories;

import com.example.BookStoreSpring.entities.Library;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository()
public interface LibraryRepository extends JpaRepository<Library, Long> {
}
