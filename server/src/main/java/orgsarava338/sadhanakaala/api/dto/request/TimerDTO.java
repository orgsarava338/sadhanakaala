package orgsarava338.sadhanakaala.api.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

import orgsarava338.sadhanakaala.domain.timer.Recurrence;
import orgsarava338.sadhanakaala.domain.timer.Segment;
import orgsarava338.sadhanakaala.domain.timer.Sound;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TimerDTO {

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

    private List<String> sharedWith;

    private List<String> tags;

    private String timezone;

    private Recurrence recurrence;

    @Builder.Default
    private Boolean archived = false;

    private Map<String, Object> meta;
}
