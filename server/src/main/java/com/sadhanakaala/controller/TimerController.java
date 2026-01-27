package com.sadhanakaala.controller;

import com.sadhanakaala.model.Timer;
import com.sadhanakaala.service.TimerService;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/timers")
public class TimerController {

    @Autowired
    private TimerService timerService;

    @PostMapping
    public ResponseEntity<Timer> createTimer(@Valid @RequestBody @NonNull Timer timer) {
        Timer saved = timerService.createTimer(timer);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/by-owner/{ownerId}")
    public ResponseEntity<List<Timer>> listByOwner(@PathVariable @NonNull String ownerId) {
        return ResponseEntity.ok(timerService.listByOwner(ownerId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Timer> get(@PathVariable @NonNull String id) {
        return ResponseEntity.ok(timerService.getTimer(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Timer> update(
        @PathVariable @NonNull String id, 
        @Valid @RequestBody @NonNull Timer updates, 
        @RequestHeader("X-User-Id") @NonNull String actorUserId) {
            Timer updated = timerService.updateTimer(id, updates, actorUserId);
            return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
        @PathVariable @NonNull String id, 
        @RequestHeader("X-User-Id") @NonNull String actorUserId) {
            timerService.deleteTimer(id, actorUserId);
            return ResponseEntity.noContent().build();
    }
}