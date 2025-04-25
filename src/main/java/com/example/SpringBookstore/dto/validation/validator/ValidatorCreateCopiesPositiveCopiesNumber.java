package com.example.SpringBookstore.dto.validation.validator;

import com.example.SpringBookstore.dto.validation.customAnnotation.ValidPositiveCopiesNumber;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidatorCreateCopiesPositiveCopiesNumber implements ConstraintValidator<ValidPositiveCopiesNumber, Integer> {
    @Override
    public void initialize(ValidPositiveCopiesNumber validPositiveCopiesNumber) {
    }

    @Override
    public boolean isValid(Integer numberOfCopies, ConstraintValidatorContext context) {
        return numberOfCopies > 0;
    }
}
