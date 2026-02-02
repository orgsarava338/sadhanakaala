package orgsarava338.sadhanakaala.domain.timer;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Recurrence {
    private String rrule;
    private Instant nextRunAt;

    @Builder.Default
    private Boolean enabled = false;
}