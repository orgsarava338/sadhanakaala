package com.sadhanakaala.persistence.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.sadhanakaala.domain.user.Role;

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

    private String uid;

    private String displayName;
    private String email;
    private String photoUrl;

    private Set<Role> roles;

    @Builder.Default
    private boolean active = true;

    private Instant createdAt;
    private Instant lastLoginAt;

    public void addRole(Role role) {
        this.roles.add(role);
    }
}