package com.sadhanakaala.persistence.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.Update;

import com.sadhanakaala.persistence.entity.TimerEntity;

import java.time.Instant;
import java.util.List;

public interface TimerRepository extends MongoRepository<TimerEntity, String> {
    List<TimerEntity> findByOwnerIdOrderByLastUsedAtDesc(String ownerId);
    List<TimerEntity> findByOwnerId(String ownerId);
    
    @Query(value = "{ '_id': ?0 }")
    @Update("{ '$inc': { 'usageCount': 1 }, '$set': { 'lastUsedAt': ?1 } }")
    int incrementUsageAndTouch(String timerId, Instant now);
}