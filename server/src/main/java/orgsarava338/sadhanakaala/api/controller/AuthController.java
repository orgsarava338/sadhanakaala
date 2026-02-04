package orgsarava338.sadhanakaala.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import orgsarava338.sadhanakaala.constants.ApiConstants;
import orgsarava338.sadhanakaala.constants.HeaderConstants;
import orgsarava338.sadhanakaala.domain.oauth.AuthService;
import orgsarava338.sadhanakaala.api.dto.response.ApiResponse;
import orgsarava338.sadhanakaala.api.dto.response.User;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(ApiConstants.AUTH_API)
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ApiResponse<User> login(HttpServletRequest request, HttpServletResponse response) {
        User user = authService.login(request.getHeader(HeaderConstants.AUTHORIZATION));
        return ApiResponse.success(user);
    }

    @PostMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        authService.logout(request, response);
    }
}
