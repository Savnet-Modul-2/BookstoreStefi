package com.example.SpringBookstore.dto;

import com.example.SpringBookstore.dto.validation.customAnnotation.ValidPositiveBookingTime;
import com.example.SpringBookstore.dto.validation.information.BasicInformation;

public class CopyDTO {
    private Long id;
    private String publisher;
    @ValidPositiveBookingTime(groups = BasicInformation.class)
    private Integer maximumBookingTime;
    private BookDTO bookDTO;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getMaximumBookingTime() {
        return maximumBookingTime;
    }

    public void setMaximumBookingTime(Integer maximumBookingTime) {
        this.maximumBookingTime = maximumBookingTime;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public BookDTO getBookDTO() {
        return bookDTO;
    }

    public void setBookDTO(BookDTO bookDTO) {
        this.bookDTO = bookDTO;
    }
}
