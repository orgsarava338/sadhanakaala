package com.sadhanakaala.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sadhanakaala.constants.ApiConstants;

import java.time.Instant;
import java.util.Map;

@RestController
public class HealthController {

    @GetMapping(ApiConstants.HEALTH_API)
    public Map<String, Object> health() {
        return Map.of(
                "status", "UP",
                "service", "SadhanaKaala API",
                "timestamp", Instant.now());
    }
}
