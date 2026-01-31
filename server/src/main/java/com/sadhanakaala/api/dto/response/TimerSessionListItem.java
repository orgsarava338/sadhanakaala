package com.sadhanakaala.api.dto.response;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TimerSessionListItem {

     private String id;

    private String timerId;

    private Instant startedAt;

    private Instant endedAt;

    private Long actualDurationSeconds;

    private Boolean completed;

    private Boolean interrupted;
}
