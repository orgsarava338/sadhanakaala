package orgsarava338.sadhanakaala.persistence.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import orgsarava338.sadhanakaala.domain.timer.Recurrence;
import orgsarava338.sadhanakaala.domain.timer.Segment;
import orgsarava338.sadhanakaala.domain.timer.Sound;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.Instant;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "timers")
public class TimerEntity {
    @Id
    private String id;

    @NotBlank
    @Indexed
    private String ownerId; // store user's id (String representation of ObjectId)

    @NotBlank
    @Size(max = 200)
    private String title;

    @Size(max = 2000)
    private String description;

    @Min(1)
    private long durationSeconds;

    @Min(1)
    @Builder.Default
    private int rounds = 1;

    @Min(0)
    @Builder.Default
    private int intervalSeconds = 0;

    private List<Segment> segments;

    private Sound sound;

    @Builder.Default
    private Boolean vibrate = false;

    @Builder.Default
    private Boolean isPublic = false;

    private List<String> sharedWith; // list of userIds

    private List<String> tags;

    private String timezone;

    private Recurrence recurrence;

    @Min(0)
    @Builder.Default
    private long usageCount = 0;

    private Instant lastUsedAt;

    @Builder.Default
    private Boolean archived = false;

    private String shareToken;

    private Map<String, Object> meta;

    @NotNull
    private Instant createdAt;

    @NotNull
    private Instant updatedAt;
}