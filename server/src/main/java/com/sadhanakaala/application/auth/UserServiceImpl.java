package com.sadhanakaala.application.auth;

import com.sadhanakaala.exception.NotFoundException;
import com.sadhanakaala.persistence.entity.UserEntity;
import com.sadhanakaala.persistence.repository.UserRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserEntity requireById(@NonNull String id) {
        return userRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found: " + id));
    }
}