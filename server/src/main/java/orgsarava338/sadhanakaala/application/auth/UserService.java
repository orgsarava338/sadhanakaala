package orgsarava338.sadhanakaala.application.auth;

import java.util.List;

import org.springframework.lang.NonNull;
import org.springframework.security.core.GrantedAuthority;

import com.google.firebase.auth.FirebaseToken;
import orgsarava338.sadhanakaala.persistence.entity.UserEntity;

public interface UserService {

    UserEntity loadOrCreateUser(@NonNull FirebaseToken decodedToken);

    List<GrantedAuthority> loadAuthorities(UserEntity user);
}
