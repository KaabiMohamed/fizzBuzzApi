package com.mohamed.fizz.buzz.service;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class FizzBuzzService {

    /**
     * Generates a FizzBuzz-like sequence based on given parameters.
     * Business rule:
     * This method iterates from 1 to {limit} and applies the following rules:
     * - If a number is a multiple of {int1}, it is replaced with {str1}.
     * - If a number is a multiple of {int2}, it is replaced with {str2}.
     * - If a number is a multiple of both, it is replaced with {str1 + str2}.
     * - Otherwise, the number itself is added to the result.
     *
     * @param int1  First divisor
     * @param int2  Second divisor
     * @param limit Upper bound for iteration
     * @param str1  Replacement for multiples of {int1}
     * @param str2  Replacement for multiples of {int2}
     * @return A comma-separated FizzBuzz sequence
     */
    public String fizzBuzzGame(int int1, int int2, int limit, String str1, String str2) {

        List<String> result = new ArrayList<>();

        for (int i = 1; i <= limit; i++) {
            StringBuilder output = new StringBuilder();

            if (i % int1 == 0) {
                output.append(str1);
            }
            if (i % int2 == 0) {
                output.append(str2);
            }

            result.add(output.isEmpty() ? String.valueOf(i) : output.toString());
        }

        return String.join(",", result);
    }
}
