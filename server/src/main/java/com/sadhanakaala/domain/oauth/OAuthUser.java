package com.sadhanakaala.domain.oauth;

import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public class OAuthUser {

    private String email;
    private String name;
    private String picture;
}
