package com.sadhanakaala.service.impl;

import com.sadhanakaala.model.User;
import com.sadhanakaala.repository.UserRepository;
import com.sadhanakaala.service.UserService;
import com.sadhanakaala.exception.NotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepository userRepository;

    @Override
    public User requireById(@NonNull String id) {
        return userRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found: " + id));
    }
}