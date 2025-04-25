package com.example.SpringBookstore.dto.validation.customAnnotation;

import com.example.SpringBookstore.dto.validation.validator.ValidatorReservationDateInTheFuture;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Constraint(validatedBy = ValidatorReservationDateInTheFuture.class)
@Target(value = ElementType.TYPE)
@Retention(value = RUNTIME)
public @interface ValidDateInTheFuture {
    String message() default "The start date and the end date must be set in the future.";

    Class<? extends Payload>[] payload() default {};

    Class<?>[] groups() default {};
}
