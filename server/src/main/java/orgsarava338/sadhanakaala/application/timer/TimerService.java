package orgsarava338.sadhanakaala.application.timer;

import java.util.List;

import org.springframework.lang.NonNull;

import orgsarava338.sadhanakaala.api.dto.request.TimerDTO;
import orgsarava338.sadhanakaala.api.dto.response.Timer;
import orgsarava338.sadhanakaala.api.dto.response.TimerListItem;

public interface TimerService {
    Timer createTimer(@NonNull TimerDTO timer);

    Timer updateTimer(@NonNull String id, @NonNull TimerDTO updates, @NonNull String actorUserId);

    Timer getTimer(@NonNull String id);

    List<TimerListItem> listByOwner(@NonNull String ownerId);

    void deleteTimer(@NonNull String id, @NonNull String actorUserId);

    Timer incrementUsageAndTouch(@NonNull String timerId);
}