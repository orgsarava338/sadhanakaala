package orgsarava338.sadhanakaala.persistence.entity;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import orgsarava338.sadhanakaala.domain.user.Role;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "users")
public class UserEntity {

    @Id
    private String id;

    private String uid;

    private Set<Role> roles;

    @Builder.Default
    private boolean active = true;

    @Builder.Default
    private Instant createdAt = Instant.now();
    @Builder.Default
    private Instant lastLoginAt = Instant.now();

    public void addRole(Role role) {
        if (this.roles == null)
            this.roles = new HashSet<>();
        this.roles.add(role);
    }
}