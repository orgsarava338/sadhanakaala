package com.sadhanakaala.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.Instant;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "timer_sessions")
public class TimerSession {
    @Id
    private String id;

    @NotBlank
    private String timerId;

    @NotBlank
    private String ownerId;

    @NotNull
    private Instant startedAt;

    private Instant endedAt;

    @Min(1)
    private Long expectedDurationSeconds;

    @Min(0)
    private Long actualDurationSeconds;

    private Boolean completed;

    private Boolean interrupted;

    private String notes;

    private Map<String, Object> device;

    @NotNull
    private Instant createdAt;

    private Instant updatedAt;
}