package com.sadhanakaala.persistence.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.sadhanakaala.persistence.entity.TimerSessionEntity;

import java.util.List;

public interface TimerSessionRepository extends MongoRepository<TimerSessionEntity, String> {
    List<TimerSessionEntity> findByOwnerIdOrderByStartedAtDesc(String ownerId);
    List<TimerSessionEntity> findByTimerId(String timerId);
    List<TimerSessionEntity> findByOwnerIdOrderByStartedAtAsc(String userId);
}