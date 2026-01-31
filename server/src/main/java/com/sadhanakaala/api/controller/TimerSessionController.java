package com.sadhanakaala.api.controller;

import com.sadhanakaala.api.dto.request.TimerSessionDTO;
import com.sadhanakaala.api.dto.response.ApiResponse;
import com.sadhanakaala.api.dto.response.TimerSession;
import com.sadhanakaala.application.session.TimerSessionService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/sessions")
@RequiredArgsConstructor
public class TimerSessionController {

    private final TimerSessionService sessionService;

    @PostMapping("/start")
    public ApiResponse<TimerSession> startSession(@Valid @RequestBody @NonNull TimerSessionDTO sessionDTO) {
        TimerSession saved = sessionService.startSession(sessionDTO);
        return ApiResponse.<TimerSession>builder().data(saved).build();
    }

    @PostMapping("/complete/{sessionId}")
    public ApiResponse<TimerSession> completeSession(
        @PathVariable @NonNull String sessionId, 
            @RequestBody @NonNull TimerSessionDTO update,
        @RequestHeader("X-User-Id") @NonNull String actorUserId) {

            TimerSession completed = sessionService.completeSession(sessionId, update, actorUserId);
            return ApiResponse.<TimerSession>builder().data(completed).build();
    }

    @GetMapping("/{id}")
    public ApiResponse<TimerSession> get(@PathVariable @NonNull String id) {
        return ApiResponse.<TimerSession>builder().data(sessionService.getSession(id)).build();
    }
}