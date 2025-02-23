package com.example.BookStoreSpring.service;

import com.example.BookStoreSpring.entities.Book;
import com.example.BookStoreSpring.entities.Library;
import com.example.BookStoreSpring.dto.BookDTO;
import com.example.BookStoreSpring.mapper.LibraryMapper;
import com.example.BookStoreSpring.repositories.BookRepository;
import com.example.BookStoreSpring.repositories.LibraryRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service()
public class BookService {
    @Autowired()
    private BookRepository bookRepository;
    @Autowired()
    private LibraryRepository libraryRepository;

    public Book create(Book book) {
        return bookRepository.save(book);
    }

    public Book findByID(Long bookID) {
        return bookRepository.findById(bookID).orElseThrow(() -> new EntityNotFoundException("Book with ID " + bookID + " not found."));
    }

    public List<Book> listAll() {
        return bookRepository.findAll();
    }

    public Page<Book> listPaginated(Integer pageNumber, Integer numberOfElements) {
        Pageable pageable = PageRequest.of(pageNumber, numberOfElements);
        return bookRepository.findAll(pageable);
    }

    public Book update(Book bookToUpdate, BookDTO bookUpdate) {
        bookToUpdate.setIsbn(bookUpdate.getIsbn());
        bookToUpdate.setTitle(bookUpdate.getTitle());
        bookToUpdate.setAuthor(bookUpdate.getAuthor());
        bookToUpdate.setReleaseDate(bookUpdate.getReleaseDate());
        bookToUpdate.setNumberOfPages(bookUpdate.getNumberOfPages());
        bookToUpdate.setCategory(bookUpdate.getCategory());
        bookToUpdate.setLanguage(bookUpdate.getLanguage());

        if (bookUpdate.getLibraryDTO() != null) {
            bookToUpdate.setLibrary(LibraryMapper.libraryDTO2Library(bookUpdate.getLibraryDTO()));
        }

        return bookRepository.save(bookToUpdate);
    }

    public void addBook(Book book, Library library) {
        library.addBook(book);
        libraryRepository.save(library);
    }

    public void removeBook(Book book, Library library) {
        library.removeBook(book);
        libraryRepository.save(library);
    }
}
