package orgsarava338.sadhanakaala.persistence.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

import java.time.Instant;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "timer_sessions")
public class TimerSessionEntity {
    @Id
    private String id;

    @NotBlank
    private String timerId;

    @NotBlank
    private String ownerId;

    @Builder.Default
    private Instant startedAt = Instant.now();

    private Instant endedAt;

    @Min(1)
    private Long expectedDurationSeconds;

    @Min(0)
    private Long actualDurationSeconds;

    @Builder.Default
    private Boolean completed = false;

    private Boolean interrupted;

    private String notes;

    private Map<String, Object> device;

    @Builder.Default
    private Instant createdAt = Instant.now();

    private Instant updatedAt;
}