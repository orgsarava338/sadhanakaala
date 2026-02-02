package orgsarava338.sadhanakaala.api.dto.response;

import java.time.Instant;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TimerSession {
    private String id;

    private String timerId;

    private String ownerId;

    private Instant startedAt;

    private Instant endedAt;

    private Long expectedDurationSeconds;

    private Long actualDurationSeconds;

    private Boolean completed;

    private Boolean interrupted;

    private String notes;

    private Map<String, Object> device;

    private Instant createdAt;

    private Instant updatedAt;

}
