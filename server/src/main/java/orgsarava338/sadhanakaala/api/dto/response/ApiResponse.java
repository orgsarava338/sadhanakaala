package orgsarava338.sadhanakaala.api.dto.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {
    private T data;
    private List<Error> errors;
    private List<Warning> warnings;

    public static <T> ApiResponse<T> success(T data) {
        return ApiResponse.<T>builder()
                .data(data)
                .errors(List.of())
                .warnings(List.of())
                .build();
    }

    public static ApiResponse<Void> error(String code, String message) {
        return ApiResponse.<Void>builder()
                .data(null)
                .errors(List.of(new Error(code, message)))
                .warnings(List.of())
                .build();
    }

}
