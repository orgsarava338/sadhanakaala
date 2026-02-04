package orgsarava338.sadhanakaala.api.dto.response;

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
    private String displayName;
    private String photoUrl;
    private Set<Role> roles;
}
