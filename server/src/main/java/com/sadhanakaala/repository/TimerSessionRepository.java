package com.sadhanakaala.repository;

import com.sadhanakaala.model.TimerSession;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface TimerSessionRepository extends MongoRepository<TimerSession, String> {
    List<TimerSession> findByOwnerIdOrderByStartedAtDesc(String ownerId);
    List<TimerSession> findByTimerId(String timerId);
}