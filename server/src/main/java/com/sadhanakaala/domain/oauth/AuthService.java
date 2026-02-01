package com.sadhanakaala.domain.oauth;

import org.springframework.stereotype.Service;

import com.sadhanakaala.constants.HeaderConstants;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    public void login(HttpServletRequest request, HttpServletResponse response) {
        response.addCookie(new Cookie(HeaderConstants.SESSION_ID, ""));
        throw new UnsupportedOperationException("Unimplemented method 'login'");
    }

    public void logout(HttpServletRequest request, HttpServletResponse response) {
        request.getSession().invalidate();
        response.addCookie(new Cookie(HeaderConstants.SESSION_ID, ""));
    }

}
