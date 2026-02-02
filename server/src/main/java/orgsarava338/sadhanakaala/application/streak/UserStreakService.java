package orgsarava338.sadhanakaala.application.streak;

import orgsarava338.sadhanakaala.api.dto.response.Streak;

public interface UserStreakService {

    Streak getUserStreak(String userId);

}
