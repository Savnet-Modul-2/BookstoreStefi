package com.example.SpringBookstore.dto.validation.customAnnotation;

import com.example.SpringBookstore.dto.validation.validator.ValidatorCopyPositiveBookingTime;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Constraint(validatedBy = ValidatorCopyPositiveBookingTime.class)
@Target(value = ElementType.FIELD)
@Retention(value = RUNTIME)
public @interface ValidPositiveBookingTime {
    String message() default "The booking time must be greater than 0.";

    Class<? extends Payload>[] payload() default {};

    Class<?>[] groups() default {};
}
