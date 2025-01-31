package com.mohamed.fizz.buzz.monitoring;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mohamed.fizz.buzz.dto.FizzBuzzRequest;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tags;
import io.micrometer.core.instrument.search.MeterNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Map;

/**
 * Metrics service logic
 */

@Service
@RequiredArgsConstructor
@Slf4j
public class ApiMetricsService {

    private static final String METRIC_NAME = "api.calls.by.param";

    private final MeterRegistry meterRegistry;
    private final ObjectMapper objectMapper;

    public void recordApiCall(FizzBuzzRequest request) {
        String params = asJsonString(request);
        Counter.builder(METRIC_NAME)
                .description("API calls grouped by params")
                .tags(Tags.of("params", params))
                .register(meterRegistry)
                .increment();
        log.debug("Params : {} added to Counter {}", params, METRIC_NAME);
    }

    public Map<String, ? extends Serializable> getMostFrequentCall() {
        try {
            return meterRegistry.get(METRIC_NAME)
                    .counters()
                    .stream()
                    .max(Comparator.comparingDouble(Counter::count))
                    .map(counter -> {
                        String params = counter.getId().getTag("params");
                        if (params == null) {
                            throw new IllegalStateException(
                                    "Counter tag 'params' is missing for counter: " + counter.getId());
                        }
                        return Map.of(
                                "params", params,
                                "count", counter.count()
                        );
                    })
                    .orElse(Map.of("message", "No API calls recorded."));
        } catch (MeterNotFoundException e) {
            return Map.of("message", "No API calls recorded.");
        }
    }

    private String asJsonString(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (Exception e) {
            log.error("Error converting object to JSON", e);
            throw new RuntimeException(e);
        }
    }

}
