package orgsarava338.sadhanakaala.api.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.firebase.auth.FirebaseToken;

import orgsarava338.sadhanakaala.constants.ApiConstants;
import orgsarava338.sadhanakaala.constants.HeaderConstants;
import orgsarava338.sadhanakaala.api.dto.response.ApiResponse;
import orgsarava338.sadhanakaala.api.dto.response.User;
import orgsarava338.sadhanakaala.api.dto.mapper.UserMapper;
import orgsarava338.sadhanakaala.application.auth.FirebaseAuthUtil;
import orgsarava338.sadhanakaala.application.auth.UserService;
import orgsarava338.sadhanakaala.persistence.entity.UserEntity;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(ApiConstants.USER_API)
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;
    private final FirebaseAuthUtil firebaseAuthUtil;

    @GetMapping("/me")
    public ApiResponse<User> getUser(Authentication authentication) {
        UserEntity authUser = (UserEntity) authentication.getPrincipal();
        UserEntity user = userService.loadUser(authUser.getUid());
        return ApiResponse.success(userMapper.toResponse(user));
    }

    @PostMapping("/login")
    public ApiResponse<User> upsert(HttpServletRequest request, HttpServletResponse response) {
        FirebaseToken firebaseToken = firebaseAuthUtil.decodeToken(request.getHeader(HeaderConstants.AUTHORIZATION));
        UserEntity user = userService.loadOrCreateUser(firebaseToken);
        return ApiResponse.success(userMapper.toResponse(user));
    }

}
