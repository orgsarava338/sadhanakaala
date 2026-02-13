package orgsarava338.sadhanakaala.api.dto.response;

import java.time.Instant;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import orgsarava338.sadhanakaala.domain.user.Role;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    private String uid;
    private Set<Role> roles;
    private boolean active;
    private Instant lastLoginAt;
    private Instant createdAt;
}
