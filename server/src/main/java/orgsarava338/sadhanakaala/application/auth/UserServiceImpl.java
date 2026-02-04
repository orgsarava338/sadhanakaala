package orgsarava338.sadhanakaala.application.auth;

import com.google.firebase.auth.FirebaseToken;
import orgsarava338.sadhanakaala.domain.user.Role;
import orgsarava338.sadhanakaala.persistence.entity.UserEntity;
import orgsarava338.sadhanakaala.persistence.repository.UserRepository;

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
        UserEntity user = userRepository
                .findByUid(firebaseToken.getUid())
                .orElseGet(() -> {
                    UserEntity createdUser = UserEntity.builder()
                            .email(firebaseToken.getEmail())
                            .uid(firebaseToken.getUid())
                            .displayName(firebaseToken.getName())
                            .photoUrl(firebaseToken.getPicture())
                            .build();
                    log.info("user:{} , profile: {}", firebaseToken.getName(), firebaseToken.getPicture());
                    createdUser.addRole(Role.USER);
                    userRepository.save(createdUser);
                    return createdUser;
                });

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