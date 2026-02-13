package orgsarava338.sadhanakaala.application.auth;

import org.springframework.stereotype.Component;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;

@Component
public class FirebaseAuthUtil {
    
    public FirebaseToken decodeToken(String token) {
        try {
            return FirebaseAuth.getInstance().verifyIdToken(token.substring(7));
        } catch (FirebaseAuthException ex) {
            throw new RuntimeException("token is not valid", ex);
        }
    }
}
