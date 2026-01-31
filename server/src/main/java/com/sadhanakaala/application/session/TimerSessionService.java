package com.sadhanakaala.application.session;

import org.springframework.lang.NonNull;

import com.sadhanakaala.api.dto.request.TimerSessionDTO;
import com.sadhanakaala.api.dto.response.TimerSession;

public interface TimerSessionService {
    TimerSession startSession(@NonNull TimerSessionDTO sessionDTO);
    TimerSession completeSession(@NonNull String sessionId, @NonNull TimerSessionDTO update, @NonNull String actorUserId);
    TimerSession getSession(@NonNull String sessionId);
}