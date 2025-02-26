package com.example.BookStoreSpring.repositories;

import com.example.BookStoreSpring.entities.Exemplary;
import com.example.BookStoreSpring.entities.Librarian;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ExemplaryRepository extends JpaRepository<Exemplary, Long> {

}
