package orgsarava338.sadhanakaala.api.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Warning {
    private String code;
    private String message;
}
