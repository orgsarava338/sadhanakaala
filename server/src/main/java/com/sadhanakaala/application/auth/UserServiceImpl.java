package com.sadhanakaala.application.auth;

import com.google.firebase.auth.FirebaseToken;
import com.sadhanakaala.domain.user.Role;
import com.sadhanakaala.persistence.entity.UserEntity;
import com.sadhanakaala.persistence.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.util.LinkedList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserEntity loadOrCreateUser(FirebaseToken firebaseToken) {
        log.info("firebase Token: {}", firebaseToken.toString());

        return userRepository
                .findByUid(firebaseToken.getUid())
                .orElseGet(() -> {
                    UserEntity user = UserEntity.builder()
                            .email(firebaseToken.getEmail())
                            .uid(firebaseToken.getUid())
                            .displayName(firebaseToken.getName())
                            .lastLoginAt(Instant.now())
                            .createdAt(Instant.now())
                            .build();
                    user.addRole(Role.USER);

                    return userRepository.save(user);
                });
    }

    @Override
    public List<GrantedAuthority> loadAuthorities(UserEntity user) {
        List<GrantedAuthority> authorities = new LinkedList<>();

        for (Role role : user.getRoles()) {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.name()));
        }

        return authorities;
    }

}