package orgsarava338.sadhanakaala.domain.timer;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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