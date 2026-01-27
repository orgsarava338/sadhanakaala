package com.sadhanakaala.constants;

public final class DbConstants {

    // DB collection names
    public static final String USERS_COLLECTION = "users";
    public static final String TIMERS_COLLECTION = "timers";
    public static final String TIMER_SESSIONS_COLLECTION = "timer_sessions";

    // Collection validator paths
    public static final String USERS_VALIDATOR_PATH = "mongo/validators/users.validator.json";
    public static final String TIMERS_VALIDATOR_PATH = "mongo/validators/timers.validator.json";
    public static final String TIMER_SESSIONS_VALIDATOR_PATH = "mongo/validators/timer_sessions.validator.json";

    // Collection indexes paths
    public static final String USERS_INDEXES_PATH = "mongo/indexes/users.indexes.json";
    public static final String TIMERS_INDEXES_PATH = "mongo/indexes/timers.indexes.json";
    public static final String TIMER_SESSIONS_INDEXES_PATH = "mongo/indexes/timer_sessions.indexes.json";

    // DB default values
    public static final int DEFAULT_PAGE_SIZE = 20;
    public static final int MAX_PAGE_SIZE = 200;

}
