package com.example.SpringBookstore.dto.validation.validator;

import com.example.SpringBookstore.dto.ReservationDTO;
import com.example.SpringBookstore.dto.validation.customAnnotation.ValidDateOrder;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidatorReservationDateOrder implements ConstraintValidator<ValidDateOrder, ReservationDTO> {
    @Override
    public void initialize(ValidDateOrder validDateOrder) {
    }

    @Override
    public boolean isValid(ReservationDTO reservationDTO, ConstraintValidatorContext context) {
        return reservationDTO.getStartDate().isBefore(reservationDTO.getEndDate());
    }
}
