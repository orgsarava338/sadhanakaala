package orgsarava338.sadhanakaala.application.session;

import orgsarava338.sadhanakaala.api.dto.mapper.TimerSessionMapper;
import orgsarava338.sadhanakaala.api.dto.request.TimerSessionDTO;
import orgsarava338.sadhanakaala.api.dto.response.TimerSession;
import orgsarava338.sadhanakaala.application.timer.TimerService;
import orgsarava338.sadhanakaala.exception.AccessDeniedException;
import orgsarava338.sadhanakaala.exception.BadRequestException;
import orgsarava338.sadhanakaala.exception.NotFoundException;
import orgsarava338.sadhanakaala.persistence.entity.TimerEntity;
import orgsarava338.sadhanakaala.persistence.entity.TimerSessionEntity;
import orgsarava338.sadhanakaala.persistence.repository.TimerRepository;
import orgsarava338.sadhanakaala.persistence.repository.TimerSessionRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.Instant;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TimerSessionServiceImpl implements TimerSessionService {

    private final TimerSessionMapper sessionMapper;
    private final TimerSessionRepository sessionRepository;

    @Autowired
    private TimerRepository timerRepository;

    @Autowired
    private TimerService timerService;

    @Override
    public TimerSession startSession(@NonNull TimerSessionDTO sessionDTO) {
        String timerId = sessionDTO.getTimerId();
        String ownerId = sessionDTO.getOwnerId();
        if (timerId == null || !StringUtils.hasText(timerId))
            throw new BadRequestException("timerId is required in the timer session");
        if (ownerId == null || !StringUtils.hasText(ownerId))
            throw new BadRequestException("ownerId is required in the timer session");

        TimerEntity timer = timerRepository.findById(timerId)
                .orElseThrow(() -> new NotFoundException("Timer not found: " + timerId));
        if (!timer.getOwnerId().equals(ownerId)
                && (timer.getSharedWith() == null || !timer.getSharedWith().contains(ownerId))) {
            throw new AccessDeniedException("User doesn't have access to timer");
        }

        TimerSessionEntity session = sessionMapper.toEntity(sessionDTO);
        session.setCreatedAt(Instant.now());
        session.setUpdatedAt(Instant.now());
        session.setStartedAt(session.getStartedAt() != null ? session.getStartedAt() : Instant.now());
        return sessionMapper.toResponse(sessionRepository.save(session));
    }

    @Override
    public TimerSession completeSession(@NonNull String sessionId, @NonNull TimerSessionDTO update,
            @NonNull String actorUserId) {
        if (!StringUtils.hasText(sessionId))
            throw new BadRequestException("sessionId is can not be empty");
        if (!StringUtils.hasText(actorUserId))
            throw new BadRequestException("actorUserId is can not be empty");

        Optional<TimerSessionEntity> maybe = sessionRepository.findById(sessionId);
        if (maybe.isEmpty())
            throw new NotFoundException("Session not found: " + sessionId);
        TimerSessionEntity session = maybe.get();
        String timerId = session.getTimerId();
        if (timerId == null || !StringUtils.hasText(timerId))
            throw new BadRequestException("Session has invalid timerId");
        if (!session.getOwnerId().equals(actorUserId))
            throw new AccessDeniedException("Only session owner can complete it");

        session.setEndedAt(update.getEndedAt() != null ? update.getEndedAt() : Instant.now());
        session.setActualDurationSeconds(update.getActualDurationSeconds() != null ? update.getActualDurationSeconds()
                : session.getActualDurationSeconds());
        session.setCompleted(Boolean.TRUE.equals(update.getCompleted()));
        session.setInterrupted(update.getInterrupted() != null ? update.getInterrupted() : session.getInterrupted());
        session.setNotes(update.getNotes() != null ? update.getNotes() : session.getNotes());
        session.setUpdatedAt(Instant.now());
        // Increment usage on timer (atomic in TimerService)
        timerService.incrementUsageAndTouch(timerId);

        return sessionMapper.toResponse(sessionRepository.save(session));
    }

    @Override
    public TimerSession getSession(@NonNull String sessionId) {
        if (!StringUtils.hasText(sessionId))
            throw new BadRequestException("sessionId is can not be empty");
        return sessionMapper.toResponse(sessionRepository.findById(sessionId)
                .orElseThrow(() -> new NotFoundException("Session not found: " + sessionId)));
    }
}