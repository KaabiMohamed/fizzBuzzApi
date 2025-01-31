package com.mohamed.fizz.buzz.monitoring;

import com.mohamed.fizz.buzz.dto.FizzBuzzRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * Execute around {@MonitorRequestArgs} to store metrics around calls args
 */
@Aspect
@Component
@Slf4j
@RequiredArgsConstructor
public class ApiMetricsAspect {

    private final ApiMetricsService apiMetricsService;

    @Around("@annotation(monitorRequestArgs) && args(requestBody,..)")
    public Object trackFizzBuzzRequest(ProceedingJoinPoint joinPoint, Object requestBody, MonitorRequestArgs monitorRequestArgs) throws Throwable {
        if (Objects.requireNonNull(requestBody) instanceof FizzBuzzRequest fizzBuzzRequest) {
            apiMetricsService.recordApiCall(fizzBuzzRequest);
        } else {
            throw new IllegalMonitorStateException("Monitoring class " + requestBody.getClass().getName() + " is not supported");
        }
        return joinPoint.proceed();

    }


}
