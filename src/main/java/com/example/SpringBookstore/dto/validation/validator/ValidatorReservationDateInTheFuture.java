package com.example.SpringBookstore.dto.validation.validator;

import com.example.SpringBookstore.dto.ReservationDTO;
import com.example.SpringBookstore.dto.validation.customAnnotation.ValidDateInTheFuture;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;

public class ValidatorReservationDateInTheFuture implements ConstraintValidator<ValidDateInTheFuture, ReservationDTO> {
    @Override
    public void initialize(ValidDateInTheFuture validDateInTheFuture) {
    }

    @Override
    public boolean isValid(ReservationDTO reservationDTO, ConstraintValidatorContext context) {
        return reservationDTO.getStartDate().isAfter(LocalDate.now()) && reservationDTO.getEndDate().isAfter(LocalDate.now());
    }
}
