package orgsarava338.sadhanakaala.api.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class Streak {

    private int currentStreakDays; // consecutive completed sessions
    private int longestStreakDays; // historical max streak
    private LocalDate lastSessionDate; // last completed session
    private int missedDays; // number of missed sessions
    private double avgRecoveryDays; // average recovery days
}
