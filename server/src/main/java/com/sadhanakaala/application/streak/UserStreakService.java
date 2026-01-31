package com.sadhanakaala.application.streak;

import com.sadhanakaala.api.dto.response.Streak;

public interface UserStreakService {

    Streak getUserStreak(String userId);

}
