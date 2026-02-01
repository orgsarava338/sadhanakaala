package com.sadhanakaala.infra.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Environment-configurable application properties.
 *
 * Bind values from application.yml using prefix "sadhanakaala".
 */
@ConfigurationProperties(prefix = "sadhanakaala")
@Getter
public class ApplicationProperties {

    private Defaults defaults;

    @Getter
    @AllArgsConstructor
    public static class Defaults {
        private String timezone = "UTC";
        private int pageSize = 20;
        private int maxPageSize = 200;
    }
}