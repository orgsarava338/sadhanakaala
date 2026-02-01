package com.sadhanakaala.api.controller;

import com.sadhanakaala.api.dto.request.TimerDTO;
import com.sadhanakaala.api.dto.response.ApiResponse;
import com.sadhanakaala.api.dto.response.Timer;
import com.sadhanakaala.api.dto.response.TimerListItem;
import com.sadhanakaala.application.timer.TimerService;
import com.sadhanakaala.constants.ApiConstants;

import lombok.RequiredArgsConstructor;

import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(ApiConstants.TIMERS_API)
@RequiredArgsConstructor
public class TimerController {

    private final TimerService timerService;

    @PostMapping
    public ApiResponse<Timer> createTimer(@RequestBody @NonNull TimerDTO timerDTO) {
        Timer saved = timerService.createTimer(timerDTO);
        return ApiResponse.<Timer>builder().data(saved).build();
    }

    @GetMapping("/by-owner/{ownerId}")
    public ApiResponse<List<TimerListItem>> listByOwner(@PathVariable @NonNull String ownerId) {
        return ApiResponse.<List<TimerListItem>>builder().data(timerService.listByOwner(ownerId)).build();
    }

    @GetMapping("/{id}")
    public ApiResponse<Timer> get(@PathVariable @NonNull String id) {
        return ApiResponse.<Timer>builder().data(timerService.getTimer(id)).build();
    }

    @PutMapping("/{id}")
    public ApiResponse<Timer> update(
        @PathVariable @NonNull String id, 
            @RequestBody @NonNull TimerDTO updates,
        @RequestHeader("X-User-Id") @NonNull String actorUserId) {
        Timer updated = timerService.updateTimer(id, updates, actorUserId);
        return ApiResponse.<Timer>builder().data(updated).build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(
        @PathVariable @NonNull String id, 
        @RequestHeader("X-User-Id") @NonNull String actorUserId) {
            timerService.deleteTimer(id, actorUserId);
            return ApiResponse.<Void>builder().build();
    }
}