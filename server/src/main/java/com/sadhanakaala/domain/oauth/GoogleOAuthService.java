package com.sadhanakaala.domain.oauth;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class GoogleOAuthService implements OAuthClient {

    private static final String CLIENT_ID = "YOUR_CLIENT_ID";
    private static final String CLIENT_SECRET = "YOUR_CLIENT_SECRET";
    private static final String REDIRECT_URI = "http://localhost:8080/auth/callback/google";
    private static final String TOKEN_URI = "https://oauth2.googleapis.com/token";
    private static final String USERINFO_URI = "https://www.googleapis.com/oauth2/v2/userinfo";

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    @Override
    public String getAccessToken(String code) throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("code", code);
        body.add("client_id", CLIENT_ID);
        body.add("client_secret", CLIENT_SECRET);
        body.add("redirect_uri", REDIRECT_URI);
        body.add("grant_type", "authorization_code");

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(TOKEN_URI, request, String.class);

        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new RuntimeException("Failed to fetch access token from Google");
        }

        JsonNode tokenJson = objectMapper.readTree(response.getBody());
        if (tokenJson.has("error")) {
            throw new RuntimeException("Google token error: " + tokenJson.get("error").asText());
        }

        return tokenJson.get("access_token").asText();
    }

    @Override
    public OAuthUser getUserInfo(String accessToken) throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);

        HttpEntity<String> request = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(USERINFO_URI, HttpMethod.GET, request, String.class);

        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new RuntimeException("Failed to fetch user info from Google");
        }

        JsonNode userJson = objectMapper.readTree(response.getBody());
        return OAuthUser.builder()
                .email(userJson.get("email").asText())
                .name(userJson.get("name").asText())
                .picture(userJson.has("picture") ? userJson.get("picture").asText() : null)
                .build();
    }

    public String getAuthorizationUrl() {
        return "https://accounts.google.com/o/oauth2/v2/auth?"
                + "client_id=" + CLIENT_ID + "&"
                + "redirect_uri=" + REDIRECT_URI + "&"
                + "response_type=code&scope=openid email profile";
    }
}
