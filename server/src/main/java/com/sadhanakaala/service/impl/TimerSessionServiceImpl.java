package com.sadhanakaala.service.impl;

import com.sadhanakaala.exception.AccessDeniedException;
import com.sadhanakaala.exception.BadRequestException;
import com.sadhanakaala.exception.NotFoundException;
import com.sadhanakaala.model.Timer;
import com.sadhanakaala.model.TimerSession;
import com.sadhanakaala.repository.TimerRepository;
import com.sadhanakaala.repository.TimerSessionRepository;
import com.sadhanakaala.service.TimerService;
import com.sadhanakaala.service.TimerSessionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.Instant;
import java.util.Optional;

@Service
public class TimerSessionServiceImpl implements TimerSessionService {

    @Autowired
    private TimerSessionRepository sessionRepository;

    @Autowired
    private TimerRepository timerRepository;
    
    @Autowired
    private TimerService timerService;

    @Override
    public TimerSession startSession(@NonNull TimerSession session) {
        String timerId = session.getTimerId();
        String ownerId = session.getOwnerId();
        if (timerId == null || !StringUtils.hasText(timerId)) throw new BadRequestException("timerId is required in the session");
        if (ownerId == null || !StringUtils.hasText(ownerId)) throw new BadRequestException("ownerId is required in the session");

        Timer timer = timerRepository.findById(timerId).orElseThrow(() -> new NotFoundException("Timer not found: " + timerId));
        if (!timer.getOwnerId().equals(ownerId) && (timer.getSharedWith() == null || !timer.getSharedWith().contains(ownerId))) {
            throw new AccessDeniedException("User doesn't have access to timer");
        }

        Instant now = Instant.now();
        session.setCreatedAt(now);
        session.setStartedAt(now);
        return sessionRepository.save(session);
    }

    @Override
    public TimerSession completeSession(@NonNull String sessionId, @NonNull TimerSession update, @NonNull String actorUserId) {
        if (!StringUtils.hasText(sessionId)) throw new BadRequestException("sessionId is can not be empty");
        if (!StringUtils.hasText(actorUserId)) throw new BadRequestException("actorUserId is can not be empty");

        Optional<TimerSession> maybe = sessionRepository.findById(sessionId);
        if (maybe.isEmpty()) throw new NotFoundException("Session not found: " + sessionId);
        TimerSession session = maybe.get();
        String timerId = session.getTimerId();
        if (timerId == null || !StringUtils.hasText(timerId)) throw new BadRequestException("Session has invalid timerId");
        if (!session.getOwnerId().equals(actorUserId)) throw new AccessDeniedException("Only session owner can complete it");

        session.setEndedAt(update.getEndedAt() != null ? update.getEndedAt() : Instant.now());
        session.setActualDurationSeconds(update.getActualDurationSeconds() != null ? update.getActualDurationSeconds() : session.getActualDurationSeconds());
        session.setCompleted(Boolean.TRUE.equals(update.getCompleted()));
        session.setInterrupted(update.getInterrupted() != null ? update.getInterrupted() : session.getInterrupted());
        session.setNotes(update.getNotes() != null ? update.getNotes() : session.getNotes());
        session.setUpdatedAt(Instant.now());
        TimerSession saved = sessionRepository.save(session);

        // Increment usage on timer (atomic in TimerService)
        timerService.incrementUsageAndTouch(timerId);

        return saved;
    }

    @Override
    public TimerSession getSession(@NonNull String sessionId) {
        if (!StringUtils.hasText(sessionId)) throw new BadRequestException("sessionId is can not be empty");
        return sessionRepository.findById(sessionId).orElseThrow(() -> new NotFoundException("Session not found: " + sessionId));
    }
}