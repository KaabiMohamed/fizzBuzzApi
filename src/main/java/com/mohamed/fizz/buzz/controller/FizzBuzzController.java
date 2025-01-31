package com.mohamed.fizz.buzz.controller;

import com.mohamed.fizz.buzz.dto.FizzBuzzRequest;
import com.mohamed.fizz.buzz.monitoring.ApiMetricsService;
import com.mohamed.fizz.buzz.monitoring.MonitorRequestArgs;
import com.mohamed.fizz.buzz.service.FizzBuzzService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.Map;

@RestController
@RequestMapping("/api/fizzbuzz")
@RequiredArgsConstructor
public class FizzBuzzController {

    private final FizzBuzzService fizzBuzzService;
    private final ApiMetricsService apiMetricsService;

    @MonitorRequestArgs
    @PostMapping
    public ResponseEntity<String> playFizzBuzz(@Valid @RequestBody FizzBuzzRequest request) {
        String result = fizzBuzzService.fizzBuzzGame(
                request.getInt1(),
                request.getInt2(),
                request.getLimit(),
                request.getStr1(),
                request.getStr2()
        );
        return ResponseEntity.ok(result);
    }

    @GetMapping("/stats")
    public Map<String, ? extends Serializable> fizzBuzzMetrics() {
        return apiMetricsService.getMostFrequentCall();
    }


}
