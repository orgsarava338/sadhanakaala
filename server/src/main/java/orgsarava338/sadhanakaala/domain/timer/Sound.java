package orgsarava338.sadhanakaala.domain.timer;

import jakarta.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Sound {
    private String name;
    private Double volume;

    @NotBlank
    private String fileUrl;
}