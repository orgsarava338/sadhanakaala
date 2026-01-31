package com.sadhanakaala.api.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    @PostMapping("/login/google")
    public void loginByGoogle() {
        throw new RuntimeException("AuthController: loginByGoogle(): Not implemented");
    }

    @PostMapping("/logout")
    public void logout() {
        throw new RuntimeException("AuthController: logout(): Not implemented");
    }
}
