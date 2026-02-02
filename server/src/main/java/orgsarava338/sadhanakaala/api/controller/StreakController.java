package orgsarava338.sadhanakaala.api.controller;

import orgsarava338.sadhanakaala.api.dto.response.ApiResponse;
import orgsarava338.sadhanakaala.api.dto.response.Streak;
import orgsarava338.sadhanakaala.application.streak.UserStreakService;
import orgsarava338.sadhanakaala.constants.ApiConstants;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ApiConstants.STREAK_API)
@RequiredArgsConstructor
public class StreakController {

    private final UserStreakService userStreakService;

    @GetMapping("/{userId}")
    public ApiResponse<Streak> getUserStreak(@PathVariable String userId) {
        // Optionally get ZoneId from user profile; using system default for now
        return ApiResponse.<Streak>builder()
                .data(userStreakService.getUserStreak(userId))
                .build();
    }
}
