package com.sadhanakaala.persistence.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.sadhanakaala.domain.oauth.AuthProvider;

import java.time.Instant;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "users")
public class UserEntity {

    @Id
    private String id;

    private String displayName;
    private String email;
    private String photoUrl;

    private Set<AuthProvider> providers;

    @Builder.Default
    private boolean active = true;

    private Instant createdAt;
    private Instant lastLoginAt;

    public void addProvider(AuthProvider provider) {
        this.providers.add(provider);
    }
}