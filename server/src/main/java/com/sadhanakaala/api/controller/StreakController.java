package com.sadhanakaala.api.controller;

import com.sadhanakaala.api.dto.response.ApiResponse;
import com.sadhanakaala.api.dto.response.Streak;
import com.sadhanakaala.application.streak.UserStreakService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/streak")
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
