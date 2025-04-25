package com.example.SpringBookstore.dto;

import com.example.SpringBookstore.dto.validation.customAnnotation.ValidPositiveBookingTime;
import com.example.SpringBookstore.dto.validation.customAnnotation.ValidPositiveCopiesNumber;
import com.example.SpringBookstore.dto.validation.information.BasicInformation;

public class CopiesCreateDTO {
    private String publisher;
    @ValidPositiveBookingTime(groups = BasicInformation.class)
    private Integer maximumBookingTime;
    private Long bookId;
    @ValidPositiveCopiesNumber(groups = BasicInformation.class)
    private Integer numberOfCopies;

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

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public Integer getNumberOfCopies() {
        return numberOfCopies;
    }

    public void setNumberOfCopies(Integer numberOfCopies) {
        this.numberOfCopies = numberOfCopies;
    }
}
