package com.sadhanakaala.domain.oauth;

public interface OAuthClient {

    String getAccessToken(String code) throws Exception;

    OAuthUser getUserInfo(String accessToken) throws Exception;

    String getAuthorizationUrl();
}
