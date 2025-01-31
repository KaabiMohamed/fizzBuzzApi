package com.mohamed.fizz.buzz;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mohamed.fizz.buzz.dto.FizzBuzzRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class FizzBuzzGameIntegrationTest {

    private static final String BASE_URL = "/api/fizzbuzz";
    private static final String FIZZBUZZ_ENDPOINT = BASE_URL;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Value("${fizzbuzz.limit.threshold}")
    private int limitThreshold;

    @Test
    @DisplayName("Given a valid request, when calling FizzBuzz endpoint, then return correct output")
    public void givenValidRequest_whenCallingFizzBuzzEndpoint_thenReturnCorrectOutput() throws Exception {
        FizzBuzzRequest request = FizzBuzzRequest.builder()
                .int1(3)
                .int2(5)
                .limit(15)
                .str1("Fizz")
                .str2("Buzz")
                .build();

        String response = mockMvc.perform(post(FIZZBUZZ_ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        assertThat(response).isEqualTo("1,2,Fizz,4,Buzz,Fizz,7,8,Fizz,Buzz,11,Fizz,13,14,FizzBuzz");
    }

    @Test
    @DisplayName("Given missing str2, when calling FizzBuzz endpoint, then return bad request")
    public void givenMissingStringParameter_whenCallingFizzBuzzEndpoint_thenReturnBadRequest() throws Exception {
        FizzBuzzRequest request = FizzBuzzRequest.builder()
                .int1(3)
                .int2(5)
                .limit(15)
                .str1("Fizz")
                .build();

        mockMvc.perform(post(FIZZBUZZ_ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.str2").value("str2 cannot be empty"));
    }

    @Test
    @DisplayName("Given negative limit, when calling FizzBuzz endpoint, then return bad request")
    public void givenNegativeLimit_whenCallingFizzBuzzEndpoint_thenReturnBadRequest() throws Exception {
        FizzBuzzRequest request = FizzBuzzRequest.builder()
                .int1(3)
                .int2(5)
                .limit(-5)
                .str1("Fizz")
                .str2("Buzz")
                .build();

        mockMvc.perform(post(FIZZBUZZ_ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.limit").value("limit must be greater than or equal to 1"));
    }

    @Test
    @DisplayName("Given invalid multiples, when calling FizzBuzz endpoint, then return bad request")
    public void givenInvalidMultiples_whenCallingFizzBuzzEndpoint_thenReturnBadRequest() throws Exception {
        FizzBuzzRequest request = FizzBuzzRequest.builder()
                .int1(0)
                .int2(-5)
                .limit(15)
                .str1("Fizz")
                .str2("Buzz")
                .build();

        mockMvc.perform(post(FIZZBUZZ_ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.int1").value("int1 must be greater than or equal to 1"))
                .andExpect(jsonPath("$.int2").value("int2 must be greater than or equal to 1"));
    }

    @Test
    @DisplayName("Given excessive limit, when calling FizzBuzz endpoint, then return bad request")
    public void givenExcessiveLimit_whenCallingFizzBuzzEndpoint_thenReturnBadRequest() throws Exception {
        FizzBuzzRequest request = FizzBuzzRequest.builder()
                .int1(3)
                .int2(5)
                .limit(limitThreshold + 1)
                .str1("Fizz")
                .str2("Buzz")
                .build();

        mockMvc.perform(post(FIZZBUZZ_ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.limit").value(String.format("limit must be less than or equal to %d", limitThreshold)));
    }

    @Test
    @DisplayName("Given empty strings for str1 and str2, when calling FizzBuzz endpoint, then return bad request")
    public void givenEmptyStrings_whenCallingFizzBuzzEndpoint_thenReturnBadRequest() throws Exception {
        FizzBuzzRequest request = FizzBuzzRequest.builder()
                .int1(3)
                .int2(5)
                .limit(15)
                .str1("")
                .str2("")
                .build();

        mockMvc.perform(post(FIZZBUZZ_ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.str1").value("str1 cannot be empty"))
                .andExpect(jsonPath("$.str2").value("str2 cannot be empty"));
    }


}
