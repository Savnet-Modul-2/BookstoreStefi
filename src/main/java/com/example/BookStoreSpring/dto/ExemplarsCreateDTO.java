package com.example.BookStoreSpring.dto;

public class ExemplarsCreateDTO {
    private String publisher;
    private Integer maximumBookingTime;
    private Integer numberOfExemplars;
    private Long bookId;


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

    public Integer getNumberOfExemplaries() {
        return numberOfExemplars;
    }

    public void setNumberOfExemplaries(Integer numberOfExemplaries) {
        this.numberOfExemplars = numberOfExemplaries;
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public Integer getNumberOfExemplars() {
        return numberOfExemplars;
    }

    public void setNumberOfExemplars(Integer numberOfExemplars) {
        this.numberOfExemplars = numberOfExemplars;
    }
}
