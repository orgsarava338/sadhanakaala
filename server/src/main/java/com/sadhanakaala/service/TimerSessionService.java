package com.sadhanakaala.service;

import org.springframework.lang.NonNull;

import com.sadhanakaala.model.TimerSession;

public interface TimerSessionService {
    TimerSession startSession(@NonNull TimerSession session);
    TimerSession completeSession(@NonNull String sessionId, @NonNull TimerSession update, @NonNull String actorUserId);
    TimerSession getSession(@NonNull String sessionId);
}