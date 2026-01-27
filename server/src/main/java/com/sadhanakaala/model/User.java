package com.sadhanakaala.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.Instant;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "users")
public class User {
    @Id
    private String id;

    @NotBlank @Size(min = 3, max = 30)
    @Indexed(unique = true)
    private String username;

    @NotBlank @Email @Size(max = 254)
    @Indexed(unique = true)
    private String email;

    // If you store password hash (recommended to use external OAuth)
    private String passwordHash;

    @Size(max = 100)
    private String displayName;

    private String avatarUrl;

    private String timezone; // e.g. "Asia/Kolkata"

    private String locale;

    private List<String> roles;

    @Builder.Default
    private Boolean disabled = false;

    private Map<String, Object> preferences;

    private Instant lastLoginAt;

    @NotNull
    private Instant createdAt;

    @NotNull
    private Instant updatedAt;
}