package com.example.SpringBookstore.dto.validation.customAnnotation;

import com.example.SpringBookstore.dto.validation.validator.ValidatorCreateCopiesPositiveCopiesNumber;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Constraint(validatedBy = ValidatorCreateCopiesPositiveCopiesNumber.class)
@Target(value = ElementType.FIELD)
@Retention(value = RUNTIME)
public @interface ValidPositiveCopiesNumber {
    String message() default "The number of copies must be positive.";

    Class<? extends Payload>[] payload() default {};

    Class<?>[] groups() default {};
}
