package com.example.BookStoreSpring.service;

import com.example.BookStoreSpring.entities.Library;
import com.example.BookStoreSpring.repositories.LibraryRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LibraryService {
    private final LibraryRepository libraryRepository;

    @Autowired
    public LibraryService(LibraryRepository libraryRepository) {
        this.libraryRepository = libraryRepository;
    }

    public Library create(Library libraryToCreate) {
        if (libraryToCreate.getID() != null) {
            throw new RuntimeException("Cannot provide an ID when creating a new Library.");
        }

        return libraryRepository.save(libraryToCreate);
    }

    public Library findByID(Long libraryID) {
        return libraryRepository.findById(libraryID)
                .orElseThrow(() -> new EntityNotFoundException("Library with ID " + libraryID + " not found."));
    }
}
