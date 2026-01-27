package com.sadhanakaala.service;

import org.springframework.lang.NonNull;

import com.sadhanakaala.model.User;

public interface UserService {
    User requireById(@NonNull String userId);
}
