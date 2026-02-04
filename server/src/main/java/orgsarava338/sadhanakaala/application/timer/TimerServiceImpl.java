package orgsarava338.sadhanakaala.application.timer;

import orgsarava338.sadhanakaala.api.dto.mapper.TimerMapper;
import orgsarava338.sadhanakaala.api.dto.request.TimerDTO;
import orgsarava338.sadhanakaala.api.dto.response.Timer;
import orgsarava338.sadhanakaala.api.dto.response.TimerListItem;
import orgsarava338.sadhanakaala.constants.ErrorCodes;
import orgsarava338.sadhanakaala.domain.timer.Segment;
import orgsarava338.sadhanakaala.exception.AccessDeniedException;
import orgsarava338.sadhanakaala.exception.BadRequestException;
import orgsarava338.sadhanakaala.exception.NotFoundException;
import orgsarava338.sadhanakaala.persistence.entity.TimerEntity;
import orgsarava338.sadhanakaala.persistence.repository.TimerRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TimerServiceImpl implements TimerService {

    private final TimerMapper timerMapper;
    private final TimerRepository timerRepository;

    @Override
    public Timer createTimer(@NonNull TimerDTO timerDTO) {

        // Validate segments sum if provided
        if (!CollectionUtils.isEmpty(timerDTO.getSegments())) {
            long sum = timerDTO.getSegments().stream().mapToLong(Segment::getDurationSeconds).sum();
            if (sum != timerDTO.getDurationSeconds()) {
                throw new BadRequestException(ErrorCodes.INVALID_REQUEST,
                        "Sum of segment durations must equal durationSeconds");
            }
        }

        TimerEntity timerEntity = timerMapper.toEntity(timerDTO);

        Instant now = Instant.now();
        timerEntity.setCreatedAt(now);
        timerEntity.setUpdatedAt(now);
        if (timerEntity.getUsageCount() < 0)
            timerEntity.setUsageCount(0);

        return timerMapper.toResponse(timerRepository.save(timerEntity));
    }

    @Override
    public Timer updateTimer(@NonNull String id, @NonNull TimerDTO updates, @NonNull String actorUserId) {

        TimerEntity existing = timerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorCodes.TIMER_NOT_FOUND, "Timer not found: " + id));
        // Only owner can update (sharedWith editing not allowed here)
        if (!existing.getOwnerId().equals(actorUserId)) {
            throw new AccessDeniedException(ErrorCodes.ACCESS_DENIED, "Only owner can modify timer");
        }

        // Apply allowed updates (simple merge; you can make it granular)
        if (updates.getTitle() != null)
            existing.setTitle(updates.getTitle());
        if (updates.getDescription() != null)
            existing.setDescription(updates.getDescription());
        if (updates.getDurationSeconds() > 0)
            existing.setDurationSeconds(updates.getDurationSeconds());
        if (updates.getSegments() != null) {
            long sum = updates.getSegments().stream().mapToLong(Segment::getDurationSeconds).sum();
            if (sum != updates.getDurationSeconds()) {
                throw new BadRequestException(ErrorCodes.INVALID_REQUEST,
                        "Sum of segment durations must equal durationSeconds");
            }
            existing.setSegments(updates.getSegments());
        }
        existing.setRounds(updates.getRounds() == 0 ? existing.getRounds() : updates.getRounds());
        existing.setIntervalSeconds(updates.getIntervalSeconds());
        existing.setSound(updates.getSound() != null ? updates.getSound() : existing.getSound());
        existing.setIsPublic(updates.getIsPublic() != null ? updates.getIsPublic() : existing.getIsPublic());
        existing.setSharedWith(updates.getSharedWith() != null ? updates.getSharedWith() : existing.getSharedWith());
        existing.setTags(updates.getTags() != null ? updates.getTags() : existing.getTags());
        existing.setRecurrence(updates.getRecurrence() != null ? updates.getRecurrence() : existing.getRecurrence());
        existing.setArchived(updates.getArchived() != null ? updates.getArchived() : existing.getArchived());
        existing.setMeta(updates.getMeta() != null ? updates.getMeta() : existing.getMeta());

        existing.setUpdatedAt(Instant.now());
        return timerMapper.toResponse(timerRepository.save(existing));
    }

    @Override
    public Timer getTimer(@NonNull String id) {
        return timerMapper.toResponse(
                timerRepository.findById(id).orElseThrow(
                        () -> new NotFoundException(ErrorCodes.TIMER_NOT_FOUND, "Timer not found: " + id)));
    }

    @Override
    public List<TimerListItem> listByOwner(@NonNull String ownerId) {
        return timerMapper.toListItemResponse(timerRepository.findByOwnerIdOrderByLastUsedAtDesc(ownerId));
    }

    @Override
    public void deleteTimer(@NonNull String id, @NonNull String actorUserId) {
        TimerEntity existing = timerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorCodes.TIMER_NOT_FOUND, "Timer not found: " + id));
        if (!existing.getOwnerId().equals(actorUserId)) {
            throw new AccessDeniedException(ErrorCodes.ACCESS_DENIED, "Only owner can delete timer");
        }
        timerRepository.deleteById(id);
    }

    @Override
    public Timer incrementUsageAndTouch(@NonNull String timerId) {
        int updated = timerRepository.incrementUsageAndTouch(timerId, Instant.now());
        if (updated == 0)
            throw new NotFoundException(ErrorCodes.TIMER_NOT_FOUND, "Timer not found: " + timerId);
        return timerMapper.toResponse(timerRepository.findById(timerId)
                .orElseThrow(() -> new NotFoundException(ErrorCodes.TIMER_NOT_FOUND,
                        "Timer not found after increment: " + timerId)));
    }
}