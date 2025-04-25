package com.example.SpringBookstore.dto.validation.validator;

import com.example.SpringBookstore.dto.validation.customAnnotation.ValidPositiveBookingTime;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidatorCopyPositiveBookingTime implements ConstraintValidator<ValidPositiveBookingTime, Integer> {
    @Override
    public void initialize(ValidPositiveBookingTime validPositiveBookingTime) {
    }

    @Override
    public boolean isValid(Integer maximumBookingTime, ConstraintValidatorContext context) {
        return maximumBookingTime > 0;
    }
}
