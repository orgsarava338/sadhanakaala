package com.sadhanakaala.application.auth;

import org.springframework.lang.NonNull;

import com.sadhanakaala.persistence.entity.UserEntity;

public interface UserService {
    UserEntity requireById(@NonNull String userId);
}
