package orgsarava338.sadhanakaala.constants;

public class ErrorCodes {

    public static final String INVALID_REQUEST = "ERR_INVALID_REQUEST";
    public static final String INTERNAL_SERVER_ERROR = "ERR_INTERNAL_SERVER_ERROR";
    public static final String VALIDATION_ERROR = "ERR_VALIDATION_ERROR";

    // Authentication and Authorization Errors
    public static final String AUTHENTICATION_FAILED = "ERR_AUTHENTICATION_FAILED";
    public static final String ACCESS_DENIED = "ERR_ACCESS_DENIED";

    public static final String USER_NOT_FOUND = "ERR_USER_NOT_FOUND";
    public static final String USER_SIGNUP_ERROR = "ERR_USER_SIGNUP_ERROR";
    public static final String USER_LOGIN_ERROR = "ERR_USER_LOGIN_ERROR";
    public static final String USER_LOGOUT_ERROR = "ERR_USER_LOGOUT_ERROR";
    public static final String USER_PASSWORD_RESET_ERROR = "ERR_USER_PASSWORD_RESET_ERROR";
    public static final String USER_UPDATE_ERROR = "ERR_USER_UPDATE_ERROR";
    public static final String USER_DELETION_ERROR = "ERR_USER_DELETION_ERROR";

    public static final String TIMER_NOT_FOUND = "ERR_TIMER_NOT_FOUND";
    public static final String TIMER_CREATION_ERROR = "ERR_TIMER_CREATION_ERROR";
    public static final String TIMER_UPDATE_ERROR = "ERR_TIMER_UPDATE_ERROR";
    public static final String TIMER_DELETION_ERROR = "ERR_TIMER_DELETION_ERROR";

    public static final String SESSION_NOT_FOUND = "ERR_SESSION_NOT_FOUND";
    public static final String SESSION_CREATION_ERROR = "ERR_SESSION_CREATION_ERROR";
    public static final String SESSION_UPDATE_ERROR = "ERR_SESSION_UPDATE_ERROR";
    public static final String SESSION_DELETION_ERROR = "ERR_SESSION_DELETION_ERROR";

    private ErrorCodes() {
        // Prevent instantiation
    }
}
