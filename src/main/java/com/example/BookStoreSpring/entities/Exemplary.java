package com.example.BookStoreSpring.entities;

import jakarta.persistence.*;

@Entity(name = "exemplary")
@Table(name = "exemplars", schema = "public")
public class Exemplary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;
    @Column
    private String publisher;
    @Column
    private Integer maximumBookingTime;
    @ManyToOne (cascade = CascadeType.ALL)
    @JoinColumn(name = "BOOK_ID")
    private Book book;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public Integer getMaximumBookingTime() {
        return maximumBookingTime;
    }

    public void setMaximumBookingTime(Integer maximumBookingTime) {
        this.maximumBookingTime = maximumBookingTime;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

}
