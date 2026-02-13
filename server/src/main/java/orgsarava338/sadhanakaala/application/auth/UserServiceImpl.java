package orgsarava338.sadhanakaala.application.auth;

import java.time.Instant;

import com.google.firebase.auth.FirebaseToken;

import orgsarava338.sadhanakaala.constants.ErrorCodes;
import orgsarava338.sadhanakaala.domain.user.Role;
import orgsarava338.sadhanakaala.persistence.entity.UserEntity;
import orgsarava338.sadhanakaala.persistence.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import orgsarava338.sadhanakaala.exception.NotFoundException;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserEntity loadUser(String uid) {
        Optional<UserEntity> user = userRepository.findByUid(uid);
        if (user.isEmpty()) throw new NotFoundException(ErrorCodes.USER_NOT_FOUND, "User not found");
        return user.get();
    }

    @Override
    public UserEntity loadOrCreateUser(FirebaseToken firebaseToken) {
        UserEntity user = userRepository
                .findByUid(firebaseToken.getUid())
                .orElseGet(() -> {
                    UserEntity createdUser = UserEntity.builder()
                            .uid(firebaseToken.getUid())
                            .build();
                    createdUser.addRole(Role.USER);
                    userRepository.save(createdUser);
                    return createdUser;
                });

        user.setLastLoginAt(Instant.now());
        userRepository.save(user);

        return user;
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