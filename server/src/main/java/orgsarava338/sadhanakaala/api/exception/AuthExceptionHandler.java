package orgsarava338.sadhanakaala.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.google.firebase.auth.FirebaseAuthException;

import lombok.extern.slf4j.Slf4j;
import orgsarava338.sadhanakaala.api.dto.response.ApiResponse;
import orgsarava338.sadhanakaala.constants.ErrorCodes;
import orgsarava338.sadhanakaala.exception.AuthException;

@Slf4j
@RestControllerAdvice
public class AuthExceptionHandler {

    @ExceptionHandler(AuthException.class)
    public ResponseEntity<ApiResponse<Void>> handleAuth(AuthException e) {

        log.error("Auth error", e);

        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(ApiResponse.error(e.getCode(), e.getMessage()));
    }

    @ExceptionHandler(FirebaseAuthException.class)
    public ResponseEntity<ApiResponse<Void>> handleFirebase(FirebaseAuthException e) {

        log.error("Firebase auth error", e);

        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(ApiResponse.error(ErrorCodes.INVALID_AUTH_TOKEN, "Invalid or Expired login token"));
    }
}
