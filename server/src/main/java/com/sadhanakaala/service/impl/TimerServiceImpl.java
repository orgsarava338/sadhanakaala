package com.sadhanakaala.service.impl;

import com.sadhanakaala.exception.AccessDeniedException;
import com.sadhanakaala.exception.BadRequestException;
import com.sadhanakaala.exception.NotFoundException;
import com.sadhanakaala.model.Segment;
import com.sadhanakaala.model.Timer;
import com.sadhanakaala.repository.TimerRepository;
import com.sadhanakaala.service.TimerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.Instant;
import java.util.List;

@Service
public class TimerServiceImpl implements TimerService {

    @Autowired
    private TimerRepository timerRepository;

    @Override
    public Timer createTimer(@NonNull Timer timer) {

        // Validate segments sum if provided
        if (!CollectionUtils.isEmpty(timer.getSegments())) {
            long sum = timer.getSegments().stream().mapToLong(Segment::getDurationSeconds).sum();
            if (sum != timer.getDurationSeconds()) {
                throw new BadRequestException("Sum of segment durations must equal durationSeconds");
            }
        }

        Instant now = Instant.now();
        timer.setCreatedAt(now);
        timer.setUpdatedAt(now);
        if (timer.getUsageCount() < 0) timer.setUsageCount(0);
        return timerRepository.save(timer);
    }

    @Override
    public Timer updateTimer(@NonNull String id, @NonNull Timer updates, @NonNull String actorUserId) {

        Timer existing = timerRepository.findById(id).orElseThrow(() -> new NotFoundException("Timer not found: " + id));
        // Only owner can update (sharedWith editing not allowed here)
        if (!existing.getOwnerId().equals(actorUserId)) {
            throw new AccessDeniedException("Only owner can modify timer");
        }

        // Apply allowed updates (simple merge; you can make it granular)
        if (updates.getTitle() != null) existing.setTitle(updates.getTitle());
        if (updates.getDescription() != null) existing.setDescription(updates.getDescription());
        if (updates.getDurationSeconds() > 0) existing.setDurationSeconds(updates.getDurationSeconds());
        if (updates.getSegments() != null) {
            long sum = updates.getSegments().stream().mapToLong(Segment::getDurationSeconds).sum();
            if (sum != updates.getDurationSeconds()) {
                throw new BadRequestException("Sum of segment durations must equal durationSeconds");
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
        return timerRepository.save(existing);
    }

    @Override
    public Timer getTimer(@NonNull String id) {
        return timerRepository.findById(id).orElseThrow(() -> new NotFoundException("Timer not found: " + id));
    }

    @Override
    public List<Timer> listByOwner(@NonNull String ownerId) {
        return timerRepository.findByOwnerIdOrderByLastUsedAtDesc(ownerId);
    }

    @Override
    public void deleteTimer(@NonNull String id, @NonNull String actorUserId) {
        Timer existing = timerRepository.findById(id).orElseThrow(() -> new NotFoundException("Timer not found: " + id));
        if (!existing.getOwnerId().equals(actorUserId)) {
            throw new AccessDeniedException("Only owner can delete timer");
        }
        timerRepository.deleteById(id);
    }

    @Override
    public Timer incrementUsageAndTouch(@NonNull String timerId) {
        int updated = timerRepository.incrementUsageAndTouch(timerId, Instant.now());
        if (updated == 0) throw new NotFoundException("Timer not found: " + timerId);
        return timerRepository.findById(timerId).orElseThrow(() -> new NotFoundException("Timer not found after increment: " + timerId));
    }
}