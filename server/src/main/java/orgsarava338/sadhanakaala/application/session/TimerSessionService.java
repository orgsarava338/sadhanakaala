package orgsarava338.sadhanakaala.application.session;

import org.springframework.lang.NonNull;

import orgsarava338.sadhanakaala.api.dto.request.TimerSessionDTO;
import orgsarava338.sadhanakaala.api.dto.response.TimerSession;

public interface TimerSessionService {
    TimerSession startSession(@NonNull TimerSessionDTO sessionDTO);

    TimerSession completeSession(@NonNull String sessionId, @NonNull TimerSessionDTO update,
            @NonNull String actorUserId);

    TimerSession getSession(@NonNull String sessionId);
}