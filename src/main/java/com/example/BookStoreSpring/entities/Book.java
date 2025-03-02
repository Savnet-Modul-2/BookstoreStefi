package com.example.BookStoreSpring.entities;

import com.example.BookStoreSpring.BookCategory;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "book")
@Table(name = "books", schema = "public")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "ISBN")
    private Long isbn;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "AUTHOR")
    private String author;

    @Enumerated(EnumType.STRING)
    @Column(name = "CATEGORY")
    private BookCategory bookCategory;

    @Column(name = "LANGUAGE")
    private String language;

    @Column(name = "NUMBER_OF_PAGES")
    private Integer numberOfPages;

    @Column(name = "RELEASE_DATE")
    private LocalDate releaseDate;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Exemplary> exemplars = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "LIBRARY_ID")
    private Library library;

    public Long getID() {
        return id;
    }

    public void setID(Long id) {
        this.id = id;
    }

    public Long getISBN() {
        return isbn;
    }

    public void setISBN(Long isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public BookCategory getCategory() {
        return bookCategory;
    }

    public void setCategory(BookCategory bookCategory) {
        this.bookCategory = bookCategory;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Integer getNumberOfPages() {
        return numberOfPages;
    }

    public void setNumberOfPages(Integer numberOfPages) {
        this.numberOfPages = numberOfPages;
    }

    public Integer getReleaseDate() {
        return releaseDate.getYear();
    }

    public void setReleaseDate(Integer releaseDate) {
        this.releaseDate = LocalDate.ofYearDay(releaseDate, 1);
    }

    public List<Exemplary> getExemplars() {
        return exemplars;
    }

    public void setExemplars(List<Exemplary> exemplars) {
        this.exemplars = exemplars;
    }

    public Library getLibrary() {
        return library;
    }

    public void setLibrary(Library library) {
        this.library = library;
    }

    public void addExemplary(Exemplary exemplary) {
        if (!exemplars.contains(exemplary)) {
            exemplars.add(exemplary);
            exemplary.setBook(this);
        }
    }

    public void removeExemplary(Exemplary exemplary) {
        if (exemplars.contains(exemplary)) {
            exemplars.remove(exemplary);
            exemplary.setBook(null);
        }
    }
}
