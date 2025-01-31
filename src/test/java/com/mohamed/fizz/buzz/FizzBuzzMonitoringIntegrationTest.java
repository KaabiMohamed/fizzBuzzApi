package com.mohamed.fizz.buzz;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mohamed.fizz.buzz.dto.FizzBuzzRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class FizzBuzzMonitoringIntegrationTest {
    private static final String BASE_URL = "/api/fizzbuzz";
    private static final String FIZZBUZZ_ENDPOINT = BASE_URL;
    private static final String STATS_ENDPOINT = BASE_URL + "/stats";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    @DisplayName("Given multiple API calls, when calling /stats, then return the most frequent request")
    public void givenMultipleRequests_whenCallingStats_thenReturnMostFrequentRequest() throws Exception {
        // Given
        FizzBuzzRequest request1 = FizzBuzzRequest.builder()
                .int1(3)
                .int2(5)
                .limit(15)
                .str1("My")
                .str2("Test")
                .build();

        FizzBuzzRequest request2 = FizzBuzzRequest.builder()
                .int1(2)
                .int2(4)
                .limit(10)
                .str1("Foo")
                .str2("Bar")
                .build();

        for (int i = 0; i < 3; i++) {
            mockMvc.perform(post(FIZZBUZZ_ENDPOINT)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request1)))
                    .andExpect(status().isOk());
        }

        mockMvc.perform(post(FIZZBUZZ_ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request2)))
                .andExpect(status().isOk());

        // When & Then
        mockMvc.perform(get(STATS_ENDPOINT))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.params").value("{\"int1\":3,\"int2\":5,\"limit\":15,\"str1\":\"My\",\"str2\":\"Test\"}"))
                .andExpect(jsonPath("$.count").value("3.0"))
                .andReturn().getResponse().getContentAsString();

    }

}
