package com.sadhanakaala.api.dto.request;

import java.time.Instant;
import java.util.Map;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TimerSessionDTO {
    @NotBlank
    private String timerId;

    @NotBlank
    private String ownerId;

    /**
     * Optional:
     * If not provided, backend should set Instant.now()
     */
    private Instant startedAt;

    private Instant endedAt;

    @Min(1)
    private Long expectedDurationSeconds;

    @Min(0)
    private Long actualDurationSeconds;

    private Boolean completed;

    private Boolean interrupted;

    private String notes;

    /**
     * Example:
     * { "platform": "android", "model": "Pixel 7", "osVersion": "14" }
     */
    private Map<String, Object> device;
}
