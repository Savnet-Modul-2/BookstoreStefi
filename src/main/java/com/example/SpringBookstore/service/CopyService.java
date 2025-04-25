package com.example.SpringBookstore.service;

import com.example.SpringBookstore.dto.CopyDTO;
import com.example.SpringBookstore.entity.Book;
import com.example.SpringBookstore.entity.Copy;
import com.example.SpringBookstore.repository.BookRepository;
import com.example.SpringBookstore.repository.CopyRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CopyService {
    private final CopyRepository copyRepository;
    private final BookRepository bookRepository;

    @Autowired
    public CopyService(CopyRepository copyRepository, BookRepository bookRepository) {
        this.copyRepository = copyRepository;
        this.bookRepository = bookRepository;
    }

    public Copy create(Copy copyToCreate) {
        if (copyToCreate.getId() != null) {
            throw new RuntimeException("Cannot provide an ID when creating a new copy.");
        }

        return copyRepository.save(copyToCreate);
    }

    public Copy findById(Long copyId) {
        return copyRepository.findById(copyId)
                .orElseThrow(() -> new EntityNotFoundException("Copy with ID " + copyId + " not found."));
    }

    public Page<Copy> findAll(Integer pageSize) {
        Pageable pageable = pageSize != null ? PageRequest.of(0, pageSize) : Pageable.unpaged();
        return copyRepository.findAll(pageable);
    }

    public Copy update(Long copyId, CopyDTO copyDTO) {
        Copy copyToUpdate = findById(copyId);

        copyToUpdate.setPublisher(copyDTO.getPublisher());
        copyToUpdate.setMaximumBookingTime(copyDTO.getMaximumBookingTime());

        return copyRepository.save(copyToUpdate);
    }

    public void delete(Long copyId) {
        if (!copyRepository.existsById(copyId)) {
            throw new EntityNotFoundException("Cannot delete copy. Copy with ID " + copyId + " not found.");
        }

        copyRepository.deleteById(copyId);
    }

    public Page<Copy> listPaginated(Long bookId, Integer pageNumber, Integer pageSize) {
        bookRepository.findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException("Book with ID " + bookId + " not found."));

        Pageable pageable = (pageNumber != null && pageSize != null) ? PageRequest.of(pageNumber, pageSize) : Pageable.unpaged();

        return copyRepository.findByBookId(bookId, pageable);
    }

    public Copy addToBook(Long copyId, Long bookId) {
        Copy copyToAdd = findById(copyId);

        Book bookToReceiveCopy = bookRepository.findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException("Book with ID " + bookId + " not found."));

        bookToReceiveCopy.addCopy(copyToAdd);

        bookRepository.save(bookToReceiveCopy);

        return copyRepository.save(copyToAdd);
    }

    public void removeFromBook(Long copyId, Long bookId) {
        Copy copyToRemove = findById(copyId);

        Book bookToDiscardCopy = bookRepository.findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException("Book with ID " + bookId + " not found."));

        bookToDiscardCopy.removeCopy(copyToRemove);

        //copyRepository.save(copyToRemove); //not needed if orphanRemoval = true because that tells JPA to delete it automatically once it looses its relation
        bookRepository.save(bookToDiscardCopy);
    }

    public List<Copy> createMultiple(List<Copy> copiesToCreate, Long bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException("Book with ID " + bookId + " not found."));

        copiesToCreate.forEach(copy -> {
            book.addCopy(copy);
            copyRepository.save(copy);
        });

        return copiesToCreate;
    }
}
