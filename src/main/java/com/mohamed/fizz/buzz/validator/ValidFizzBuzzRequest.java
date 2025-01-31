package com.mohamed.fizz.buzz.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = FizzBuzzRequestValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidFizzBuzzRequest {
    String message() default "Invalid FizzBuzz request";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
