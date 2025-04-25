package com.example.SpringBookstore.dto;

import com.example.SpringBookstore.ReservationStatus;
import com.example.SpringBookstore.dto.validation.customAnnotation.ValidDateInTheFuture;
import com.example.SpringBookstore.dto.validation.customAnnotation.ValidDateOrder;
import com.example.SpringBookstore.dto.validation.information.AdvancedInformation;

import java.time.LocalDate;

@ValidDateInTheFuture(groups = AdvancedInformation.class)
@ValidDateOrder(groups = AdvancedInformation.class)
public class ReservationDTO {
    private Long id;
    private LocalDate startDate;
    private LocalDate endDate;
    private ReservationStatus reservationStatus;
    private CopyDTO copyDTO;
    private UserDTO userDTO;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public ReservationStatus getStatus() {
        return reservationStatus;
    }

    public void setStatus(ReservationStatus reservationStatus) {
        this.reservationStatus = reservationStatus;
    }

    public CopyDTO getCopyDTO() {
        return copyDTO;
    }

    public void setCopyDTO(CopyDTO copyDTO) {
        this.copyDTO = copyDTO;
    }

    public UserDTO getUserDTO() {
        return userDTO;
    }

    public void setUserDTO(UserDTO userDTO) {
        this.userDTO = userDTO;
    }
}
