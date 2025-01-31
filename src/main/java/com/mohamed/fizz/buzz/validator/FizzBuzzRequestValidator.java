package com.mohamed.fizz.buzz.validator;

import com.mohamed.fizz.buzz.dto.FizzBuzzRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class FizzBuzzRequestValidator implements ConstraintValidator<ValidFizzBuzzRequest, FizzBuzzRequest> {

    @Value("${fizzbuzz.limit.threshold}")
    private int limitThreshold;

    @Override
    public boolean isValid(FizzBuzzRequest request, ConstraintValidatorContext context) {
        boolean isValid = true;

        if (request.getLimit() < 1) {
            addViolation(context, "limit", "limit must be greater than or equal to 1");
            isValid = false;
        }

        if (request.getLimit() > limitThreshold) {
            addViolation(context, "limit", String.format("limit must be less than or equal to %d", limitThreshold));
            isValid = false;
        }

        if (request.getInt1() == null || request.getInt1() < 1) {
            addViolation(context, "int1", "int1 must be greater than or equal to 1");
            isValid = false;
        }

        if (request.getInt2() == null || request.getInt2() < 1) {
            addViolation(context, "int2", "int2 must be greater than or equal to 1");
            isValid = false;
        }

        return isValid;
    }

    private void addViolation(ConstraintValidatorContext context, String field, String message) {
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(message)
                .addPropertyNode(field)
                .addConstraintViolation();
    }
}
