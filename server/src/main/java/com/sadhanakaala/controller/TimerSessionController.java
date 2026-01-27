package com.sadhanakaala.controller;

import com.sadhanakaala.model.TimerSession;
import com.sadhanakaala.service.TimerSessionService;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/sessions")
public class TimerSessionController {

    @Autowired
    private TimerSessionService sessionService;

    @PostMapping("/start")
    public ResponseEntity<TimerSession> startSession(@Valid @RequestBody @NonNull TimerSession session) {
        TimerSession saved = sessionService.startSession(session);
        return ResponseEntity.ok(saved);
    }

    @PostMapping("/complete/{sessionId}")
    public ResponseEntity<TimerSession> completeSession(
        @PathVariable @NonNull String sessionId, 
        @RequestBody @NonNull TimerSession update, 
        @RequestHeader("X-User-Id") @NonNull String actorUserId) {
            TimerSession completed = sessionService.completeSession(sessionId, update, actorUserId);
            return ResponseEntity.ok(completed);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TimerSession> get(@PathVariable @NonNull String id) {
        return ResponseEntity.ok(sessionService.getSession(id));
    }
}