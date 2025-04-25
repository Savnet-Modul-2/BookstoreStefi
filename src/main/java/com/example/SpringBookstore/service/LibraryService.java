package com.example.SpringBookstore.service;

import com.example.SpringBookstore.dto.LibraryDTO;
import com.example.SpringBookstore.entity.Library;
import com.example.SpringBookstore.repository.LibraryRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class LibraryService {
    private final LibraryRepository libraryRepository;

    @Autowired
    public LibraryService(LibraryRepository libraryRepository) {
        this.libraryRepository = libraryRepository;
    }

    public Library create(Library libraryToCreate) {
        if (libraryToCreate.getId() != null) {
            throw new RuntimeException("Cannot provide an ID when creating a new library.");
        }

        return libraryRepository.save(libraryToCreate);
    }

    public Library findById(Long libraryId) {
        return libraryRepository.findById(libraryId)
                .orElseThrow(() -> new EntityNotFoundException("Library with ID " + libraryId + " not found."));
    }

    public Page<Library> findAll(Integer pageSize) {
        Pageable pageable = pageSize != null ? PageRequest.of(0, pageSize) : Pageable.unpaged();
        return libraryRepository.findAll(pageable);
    }

    public Library update(Long libraryId, LibraryDTO libraryDTO) {
        Library libraryToUpdate = findById(libraryId);

        libraryToUpdate.setName(libraryDTO.getName());
        libraryToUpdate.setAddress(libraryDTO.getAddress());

        return libraryRepository.save(libraryToUpdate);
    }

    public void delete(Long libraryId) {
        if (!libraryRepository.existsById(libraryId)) {
            throw new EntityNotFoundException("Cannot delete library. Library with ID " + libraryId + " not found.");
        }

        libraryRepository.deleteById(libraryId);
    }
}
