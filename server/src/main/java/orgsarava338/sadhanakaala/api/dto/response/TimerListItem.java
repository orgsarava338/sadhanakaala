package orgsarava338.sadhanakaala.api.dto.response;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TimerListItem {
    private String id;

    private String title;

    private long durationSeconds;

    private Boolean isPublic;

    private Boolean archived;

    private Instant updatedAt;
}
