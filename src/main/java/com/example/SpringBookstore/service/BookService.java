package com.example.SpringBookstore.service;

import com.example.SpringBookstore.dto.BookDTO;
import com.example.SpringBookstore.entity.Book;
import com.example.SpringBookstore.entity.Library;
import com.example.SpringBookstore.repository.BookRepository;
import com.example.SpringBookstore.repository.LibraryRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class BookService {
    private final BookRepository bookRepository;
    private final LibraryRepository libraryRepository;

    @Autowired
    public BookService(BookRepository bookRepository, LibraryRepository libraryRepository) {
        this.bookRepository = bookRepository;
        this.libraryRepository = libraryRepository;
    }

    public Book create(Book bookToCreate) {
        if (bookToCreate.getId() != null) {
            throw new RuntimeException("Cannot provide an ID when creating a new book.");
        }

        return bookRepository.save(bookToCreate);
    }

    public Book findById(Long bookId) {
        return bookRepository.findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException("Book with ID " + bookId + " not found."));
    }

    public Page<Book> findAll(Integer pageSize) {
        Pageable pageable = pageSize != null ? PageRequest.of(0, pageSize) : Pageable.unpaged();
        return bookRepository.findAll(pageable);
    }

    public Book update(Long bookId, BookDTO bookDTO) {
        Book bookToUpdate = findById(bookId);

        bookToUpdate.setTitle(bookDTO.getTitle());
        bookToUpdate.setAuthor(bookDTO.getAuthor());
        bookToUpdate.setCategory(bookDTO.getCategory());
        bookToUpdate.setLanguage(bookDTO.getLanguage());
        bookToUpdate.setNumberOfPages(bookDTO.getNumberOfPages());

        return bookRepository.save(bookToUpdate);
    }

    public void delete(Long bookId) {
        if (!bookRepository.existsById(bookId)) {
            throw new EntityNotFoundException("Cannot delete book. Book with ID " + bookId + " not found.");
        }

        bookRepository.deleteById(bookId);
    }

    public Page<Book> listPaginated(Integer pageNumber, Integer pageSize) {
        Pageable pageable = (pageNumber != null && pageSize != null) ? PageRequest.of(pageNumber, pageSize) : Pageable.unpaged();
        return bookRepository.findAll(pageable);
    }

    public Book addToLibrary(Long bookId, Long libraryId) {
        Book bookToAdd = findById(bookId);

        Library libraryToReceiveBook = libraryRepository.findById(libraryId)
                .orElseThrow(() -> new EntityNotFoundException("Library with ID " + libraryId + " not found."));

        libraryToReceiveBook.addBook(bookToAdd);

        libraryRepository.save(libraryToReceiveBook);

        return bookRepository.save(bookToAdd);
    }

    public void removeFromLibrary(Long bookId, Long libraryId) {
        Book bookToRemove = findById(bookId);

        Library libraryToDiscardBook = libraryRepository.findById(libraryId)
                .orElseThrow(() -> new EntityNotFoundException("Library with ID " + libraryId + " not found."));

        libraryToDiscardBook.removeBook(bookToRemove);

        //bookRepository.save(bookToRemove); //not needed if orphanRemoval = true because that tells JPA to delete it automatically once it looses its relation
        libraryRepository.save(libraryToDiscardBook);
    }

    public Book createForLibrary(Book bookToCreate, Long libraryId) {
        Library libraryToReceiveBook = libraryRepository.findById(libraryId)
                .orElseThrow(() -> new EntityNotFoundException("Library with ID " + libraryId + " not found."));

        libraryToReceiveBook.addBook(bookToCreate);

        libraryRepository.save(libraryToReceiveBook);

        return bookRepository.save(bookToCreate);
    }
}
