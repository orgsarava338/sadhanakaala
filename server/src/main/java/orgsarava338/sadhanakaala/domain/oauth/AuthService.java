package orgsarava338.sadhanakaala.domain.oauth;

import org.springframework.stereotype.Service;

import orgsarava338.sadhanakaala.api.dto.mapper.UserMapper;
import orgsarava338.sadhanakaala.api.dto.request.LoginDTO;
import orgsarava338.sadhanakaala.api.dto.response.User;
import orgsarava338.sadhanakaala.application.auth.UserService;
import orgsarava338.sadhanakaala.persistence.entity.UserEntity;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserMapper userMapper;
    private final UserService userService;

    public User login(LoginDTO loginDTO) {
        UserEntity user = userService.loadOrCreateUser(loginDTO.getToken());
        return userMapper.toResponse(user);
    }

    public void logout(HttpServletRequest request, HttpServletResponse response) {
        request.getSession().invalidate();
    }

}
