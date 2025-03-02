package com.example.BookStoreSpring.controller;

import com.example.BookStoreSpring.entities.Book;
import com.example.BookStoreSpring.entitiesDTO.BookDTO;
import com.example.BookStoreSpring.mapper.BookMapper;
import com.example.BookStoreSpring.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/books")
public class BookController {
    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody BookDTO bookDTO) {
        Book bookToCreate = BookMapper.bookDTO2Book(bookDTO);
        Book createdBook = bookService.create(bookToCreate);

        return ResponseEntity.ok(BookMapper.book2BookDTO(createdBook));
    }

    @GetMapping(path = "/{bookID}")
    public ResponseEntity<?> findByID(@PathVariable(name = "bookID") Long bookID) {
        Book foundBook = bookService.findByID(bookID);
        return ResponseEntity.ok(BookMapper.book2BookDTO(foundBook));
    }

    @GetMapping
    public ResponseEntity<?> listAll() {
        List<Book> books = bookService.listAll();

        return ResponseEntity.ok(books.stream()
                .map(BookMapper::book2BookDTO)
                .toList());
    }

    @GetMapping(path = "/paginated")
    public ResponseEntity<?> listPaginated(@RequestParam(required = false) Integer pageNumber, @RequestParam(required = false) Integer numberOfElements) {
        Page<Book> books = bookService.listPaginated(pageNumber, numberOfElements);

        return ResponseEntity.ok(books.stream()
                .map(BookMapper::book2BookDTO)
                .toList());
    }

    @PutMapping(path = "/{bookID}")
    public ResponseEntity<?> update(@PathVariable(name = "bookID") Long bookID, @RequestBody BookDTO bookDTO) {
        Book updatedBook = bookService.update(bookID, bookDTO);
        return ResponseEntity.ok((BookMapper.book2BookDTO(updatedBook)));
    }

    @PutMapping(path = "/{bookID}/{libraryID}")
    public ResponseEntity<?> addBook(@PathVariable(name = "bookID") Long bookID, @PathVariable(name = "libraryID") Long libraryID) {
        bookService.addBook(bookID, libraryID);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(path = "/{bookID}/{libraryID}")
    public ResponseEntity<?> removeBook(@PathVariable(name = "bookID") Long bookID, @PathVariable(name = "libraryID") Long libraryID) {
        bookService.removeBook(bookID, libraryID);
        return ResponseEntity.noContent().build();
    }
}
