package orgsarava338.sadhanakaala.api.controller;

import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import orgsarava338.sadhanakaala.constants.ApiConstants;
import orgsarava338.sadhanakaala.domain.oauth.AuthService;
import orgsarava338.sadhanakaala.api.dto.request.LoginDTO;
import orgsarava338.sadhanakaala.api.dto.response.ApiResponse;
import orgsarava338.sadhanakaala.api.dto.response.User;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(ApiConstants.AUTH_API)
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ApiResponse<User> login(@NonNull @RequestBody LoginDTO loginRequest) {
        User user = authService.login(loginRequest);
        return ApiResponse.<User>builder().data(user).build();
    }

    @PostMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        authService.logout(request, response);
    }
}
