package com.mohamed.fizz.buzz.dto;

import com.mohamed.fizz.buzz.validator.ValidFizzBuzzRequest;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@ValidFizzBuzzRequest
public class FizzBuzzRequest {

    @NotNull(message = "int1 cannot be null")
    @Min(value = 1, message = "int1 must be greater than or equal to 1")
    private Integer int1;

    @NotNull(message = "int2 cannot be null")
    @Min(value = 1, message = "int2 must be greater than or equal to 1")
    private Integer int2;

    @NotNull(message = "limit cannot be null")
    @Min(value = 1, message = "limit must be greater than or equal to 1")
    @Max(value = 10000, message = "limit must be less than or equal to 10000")
    private Integer limit;

    @NotBlank(message = "str1 cannot be empty")
    private String str1;

    @NotBlank(message = "str2 cannot be empty")
    private String str2;
}
