package com.example.SpringBookstore.entity;

import com.example.SpringBookstore.exceptionHandling.exception.BadRequestException;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "library")
@Table(name = "libraries", schema = "public")
public class Library {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "ADDRESS")
    private String address;

    @Column(name = "PHONENUMBER")
    private String phoneNumber;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "library")
    @JoinColumn(name = "LIBRARIAN_ID", referencedColumnName = "ID")
    private Librarian librarian;

    @OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY, mappedBy = "library", orphanRemoval = true)
    private List<Book> books = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY)
    private List<User> users;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Librarian getLibrarian() {
        return librarian;
    }

    public void setLibrarian(Librarian librarian) {
        this.librarian = librarian;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public void addBook(Book book) {
        if (!books.contains(book)) {
            books.add(book);
            book.setLibrary(this);
        } else {
            throw new BadRequestException("Library already contains book with ID " + book.getId() + ".");
        }
    }

    public void removeBook(Book book) {
        if (books.contains(book)) {
            books.remove(book);
            book.setLibrary(null);
        } else {
            throw new BadRequestException("Library does not contains book with ID " + book.getId() + ".");
        }
    }
}
