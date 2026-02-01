package com.sadhanakaala.persistence.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.sadhanakaala.persistence.entity.UserEntity;

public interface UserRepository extends MongoRepository<UserEntity, String> {

    String getUserTimeZoneById(String userId);

    Optional<UserEntity> findByEmail(String email);

    Optional<UserEntity> findByUid(String uid);
}