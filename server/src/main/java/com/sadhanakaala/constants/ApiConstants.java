package com.sadhanakaala.constants;

public final class ApiConstants {

    private static final String API = "/api";

    private static final String V1 = "/v1";

    private static final String API_V = API + V1;

    public static final String TIMERS_API = API_V + "/timer";
    public static final String SESSIONS_API = API_V + "/session";
    public static final String AUTH_API = API_V + "/auth";
    public static final String STREAK_API = API_V + "/streak";

    public static final String HEALTH_API = API_V + "/health";

    private ApiConstants() {
        // Prevent instantiation
    }
}
