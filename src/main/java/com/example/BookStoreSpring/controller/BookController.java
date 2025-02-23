package com.example.BookStoreSpring.controller;

import com.example.BookStoreSpring.entities.Book;
import com.example.BookStoreSpring.entities.Library;
import com.example.BookStoreSpring.dto.BookDTO;
import com.example.BookStoreSpring.mapper.BookMapper;
import com.example.BookStoreSpring.service.BookService;
import com.example.BookStoreSpring.service.LibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping(path = "/books")
public class BookController {
    @Autowired()
    private BookService bookService;
    @Autowired()
    private LibraryService libraryService;

    @PostMapping(path = "/create-book")
    public ResponseEntity<?> create(@RequestBody BookDTO bookDTO) {
        Book newBook = BookMapper.bookDTO2Book(bookDTO);
        Book book = bookService.create(newBook);

        return ResponseEntity.ok(BookMapper.book2BookDTO(book));
    }

    @GetMapping(path = "/find-book/{bookID}")
    public ResponseEntity<?> findByID(@PathVariable(name = "bookID") Long bookID) {
        Book book = bookService.findByID(bookID);

        return ResponseEntity.ok(BookMapper.book2BookDTO(book));
    }

    @GetMapping(path = "/list-all-books")
    public ResponseEntity<?> listAll() {
        List<Book> books = bookService.listAll();
        List<BookDTO> bookDTO = books.stream().map(BookMapper::book2BookDTO).toList();

        return ResponseEntity.ok(bookDTO);
    }

    @GetMapping(path = "/list-books-paginated")
    public ResponseEntity<?> listPaginated(@RequestParam() Integer page, @RequestParam() Integer numberOfElements) {
        Page<Book> books = bookService.listPaginated(page, numberOfElements);
        List<BookDTO> bookDTO = books.stream().map(BookMapper::book2BookDTO).toList();

        return ResponseEntity.ok(bookDTO);
    }

    @PutMapping(path = "/update-book/{bookID}")
    public ResponseEntity<?> update(@PathVariable(name = "bookID") Long bookID, @RequestBody() BookDTO bookDTO) {
        Book book = bookService.findByID(bookID);
        Book updatedBook = bookService.update(book, bookDTO);

        return ResponseEntity.ok((BookMapper.book2BookDTO(updatedBook)));
    }

    @PutMapping(path = "/add/{bookID}/to/{libraryID}")
    public ResponseEntity<?> addBook(@PathVariable(name = "bookID") Long bookID, @PathVariable(name = "libraryID") Long libraryID) {
        Book book = bookService.findByID(bookID);
        Library libraryToReceive = libraryService.findByID(libraryID);

        bookService.addBook(book, libraryToReceive);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(path = "/remove/{bookID}/from/{libraryID}")
    public ResponseEntity<?> removeBook(@PathVariable(name = "bookID") Long bookID, @PathVariable(name = "libraryID") Long libraryID) {
        Book book = bookService.findByID(bookID);
        Library libraryToDiscard = libraryService.findByID(libraryID);

        bookService.removeBook(book, libraryToDiscard);

        return ResponseEntity.noContent().build();
    }
}
