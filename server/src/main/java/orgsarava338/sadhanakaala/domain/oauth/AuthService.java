package orgsarava338.sadhanakaala.domain.oauth;

import java.time.Instant;

import org.springframework.stereotype.Service;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;

import orgsarava338.sadhanakaala.api.dto.mapper.UserMapper;
import orgsarava338.sadhanakaala.api.dto.response.User;
import orgsarava338.sadhanakaala.application.auth.UserService;
import orgsarava338.sadhanakaala.persistence.entity.UserEntity;
import orgsarava338.sadhanakaala.persistence.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserMapper userMapper;
    private final UserService userService;
    private final UserRepository userRepository;

    public User login(String token) {
        try {
            FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(token.substring(7));
            UserEntity user = userService.loadOrCreateUser(decodedToken);
            user.setLastLoginAt(Instant.now());
            userRepository.save(user);
            return userMapper.toResponse(user);
        } catch (Exception e) {
            log.error("AuthService: login(): Error: {}", e);
        }

        return null;
    }

    public void logout(HttpServletRequest request, HttpServletResponse response) {
        request.getSession().invalidate();
    }

}
