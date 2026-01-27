package com.sadhanakaala.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Segment {
    @NotBlank
    private String label;

    @Min(1)
    private long durationSeconds;
}