package com.sadhanakaala.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Environment-configurable application properties.
 *
 * Bind values from application.yml using prefix "sadhanakaala".
 */
@Component
@ConfigurationProperties(prefix = "sadhanakaala")
public class ApplicationProperties {

    private Defaults defaults = new Defaults();

    public Defaults getDefaults() { return defaults; }
    public void setDefaults(Defaults defaults) { this.defaults = defaults; }

    public static class Defaults {
        private String timezone = "UTC";
        private int pageSize = 20;
        private int maxPageSize = 200;

        public String getTimezone() { return timezone; }
        public void setTimezone(String timezone) { this.timezone = timezone; }

        public int getPageSize() { return pageSize; }
        public void setPageSize(int pageSize) { this.pageSize = pageSize; }

        public int getMaxPageSize() { return maxPageSize; }
        public void setMaxPageSize(int maxPageSize) { this.maxPageSize = maxPageSize; }
    }
}