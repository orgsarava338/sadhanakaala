package com.sadhanakaala.application.timer;

import java.util.List;

import org.springframework.lang.NonNull;

import com.sadhanakaala.api.dto.request.TimerDTO;
import com.sadhanakaala.api.dto.response.Timer;
import com.sadhanakaala.api.dto.response.TimerListItem;

public interface TimerService {
    Timer createTimer(@NonNull TimerDTO timer);
    Timer updateTimer(@NonNull String id, @NonNull TimerDTO updates, @NonNull String actorUserId);
    Timer getTimer(@NonNull String id);
    List<TimerListItem> listByOwner(@NonNull String ownerId);
    void deleteTimer(@NonNull String id, @NonNull String actorUserId);
    Timer incrementUsageAndTouch(@NonNull String timerId);
}