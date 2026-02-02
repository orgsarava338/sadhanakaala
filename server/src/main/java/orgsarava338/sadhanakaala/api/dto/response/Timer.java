package orgsarava338.sadhanakaala.api.dto.response;

import java.time.Instant;
import java.util.List;
import java.util.Map;

import orgsarava338.sadhanakaala.domain.timer.Recurrence;
import orgsarava338.sadhanakaala.domain.timer.Segment;
import orgsarava338.sadhanakaala.domain.timer.Sound;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Timer {

    private String id;

    private String ownerId;

    private String title;

    private String description;

    private long durationSeconds;

    private int rounds;

    private int intervalSeconds;

    private List<Segment> segments;

    private Sound sound;

    private Boolean vibrate;

    private Boolean isPublic;

    private List<String> sharedWith;

    private List<String> tags;

    private String timezone;

    private Recurrence recurrence;

    private long usageCount;

    private Instant lastUsedAt;

    private Boolean archived;

    private Map<String, Object> meta;

    private Instant createdAt;

    private Instant updatedAt;
}
