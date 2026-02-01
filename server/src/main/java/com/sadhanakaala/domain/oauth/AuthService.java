package com.sadhanakaala.domain.oauth;

import java.time.Instant;

import org.springframework.stereotype.Service;

import com.sadhanakaala.api.dto.mapper.UserMapper;
import com.sadhanakaala.api.dto.response.User;
import com.sadhanakaala.constants.HeaderConstants;
import com.sadhanakaala.persistence.entity.UserEntity;
import com.sadhanakaala.persistence.repository.UserRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final GoogleOAuthService googleOAuthService;

    private final OAuthClient getOauthClient(AuthProvider provider) {
        switch (provider) {
            case GOOGLE:
                return googleOAuthService;
            default:
                throw new IllegalArgumentException("Unsupported provider: " + provider);
        }
    }

    public String getProviderRedirectUrl(AuthProvider provider) {
        switch (provider) {
            case GOOGLE:
                return googleOAuthService.getAuthorizationUrl();
            default:
                throw new IllegalArgumentException("Unsupported provider: " + provider);
        }
    }

    public User handleCallback(AuthProvider provider, String code, HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        OAuthClient oauthClient = getOauthClient(provider);

        OAuthUser oauthUser = oauthClient.getUserInfo(
                oauthClient.getAccessToken(code));

        UserEntity userEntity = userRepository.findByEmail(oauthUser.getEmail())
                .orElseGet(() -> {
                    UserEntity newUser = new UserEntity();
                    newUser.setEmail(oauthUser.getEmail());
                    newUser.setDisplayName(oauthUser.getName());
                    newUser.setPhotoUrl(oauthUser.getPicture());
                    newUser.addProvider(provider);
                    newUser.setCreatedAt(Instant.now());
                    newUser.setLastLoginAt(Instant.now());
                    return userRepository.save(newUser);
                });

        return userMapper.toResponse(userEntity);
    }

    public void logout(HttpServletRequest request, HttpServletResponse response) {
        request.getSession().invalidate();
        response.setHeader("Set-Cookie", HeaderConstants.SESSION_ID + "=; Max-Age=0; Path=/; HttpOnly");
    }

    public Object getCurrentUser(HttpServletRequest request) {
        return request.getSession().getAttribute("USER_SESSION");
    }

}
