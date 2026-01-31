package com.sadhanakaala.persistence.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.sadhanakaala.persistence.entity.UserEntity;

public interface UserRepository extends MongoRepository<UserEntity, String> {

    String getUserTimeZoneByUserId(String userId);
}