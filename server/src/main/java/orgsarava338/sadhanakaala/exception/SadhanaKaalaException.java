package orgsarava338.sadhanakaala.exception;

import lombok.Getter;

@Getter
public class SadhanaKaalaException extends RuntimeException {
    private final String code;

    SadhanaKaalaException(String code, String message) {
        super(message);
        this.code = code;
    }
}
