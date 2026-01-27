package com.sadhanakaala;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main Spring Boot application entry point for SadhanaKaala.
 *
 * Notes:
 * - The application will auto-scan components under com.sadhanakaala.
 */
@SpringBootApplication
public class SadhanaKaalaApplication {

    public static void main(String[] args) {
        SpringApplication.run(SadhanaKaalaApplication.class, args);
    }
}