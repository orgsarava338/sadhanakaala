package com.sadhanakaala.repository;

import com.sadhanakaala.model.Timer;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.Update;

import java.time.Instant;
import java.util.List;

public interface TimerRepository extends MongoRepository<Timer, String> {
    List<Timer> findByOwnerIdOrderByLastUsedAtDesc(String ownerId);
    List<Timer> findByOwnerId(String ownerId);
    
    @Query(value = "{ '_id': ?0 }")
    @Update("{ '$inc': { 'usageCount': 1 }, '$set': { 'lastUsedAt': ?1 } }")
    int incrementUsageAndTouch(String timerId, Instant now);
}