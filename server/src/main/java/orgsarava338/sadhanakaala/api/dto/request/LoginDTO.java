package orgsarava338.sadhanakaala.api.dto.request;

import com.google.firebase.auth.FirebaseToken;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginDTO {
    private String name;
    private String email;
    private String uid;
    private FirebaseToken token;
}
