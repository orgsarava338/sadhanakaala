package orgsarava338.sadhanakaala.exception;

import lombok.Getter;

@Getter
public class AuthException extends RuntimeException {
    private final String code;

    AuthException(String code, String message) {
        super(message);
        this.code = code;
    }
}
