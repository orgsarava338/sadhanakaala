package com.sadhanakaala.api.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sadhanakaala.api.dto.response.ApiResponse;
import com.sadhanakaala.api.dto.response.Error;
import com.sadhanakaala.constants.ErrorCodes;
import com.sadhanakaala.domain.oauth.AuthProvider;
import com.sadhanakaala.domain.oauth.AuthService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @GetMapping("/login/{provider}")
    public void loginByProvider(@PathVariable AuthProvider provider, HttpServletResponse response)  {
        String redirectUrl = authService.getProviderRedirectUrl(provider);
        response.setHeader("Location", redirectUrl);
        response.setStatus(302);
    }

    @GetMapping("/callback/{provider}")
    public ApiResponse<?> callback(@PathVariable AuthProvider provider,
            @RequestParam(required = false) String code,
            @RequestParam(required = false) String error,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        if (error != null) {
            Error errorResponse = Error.builder().code(ErrorCodes.AUTHENTICATION_FAILED).message(error).build();
            return ApiResponse.builder().errors(List.of(errorResponse)).build();
        }

        return ApiResponse.builder().data(authService.handleCallback(provider, code, request, response)).build();
    }

    @PostMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        authService.logout(request, response);
    }
}
