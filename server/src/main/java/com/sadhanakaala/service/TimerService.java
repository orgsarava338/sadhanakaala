package com.sadhanakaala.service;

import com.sadhanakaala.model.Timer;

import java.util.List;

import org.springframework.lang.NonNull;

public interface TimerService {
    Timer createTimer(@NonNull Timer timer);
    Timer updateTimer(@NonNull String id, @NonNull Timer updates, @NonNull String actorUserId);
    Timer getTimer(@NonNull String id);
    List<Timer> listByOwner(@NonNull String ownerId);
    void deleteTimer(@NonNull String id, @NonNull String actorUserId);
    Timer incrementUsageAndTouch(@NonNull String timerId);
}