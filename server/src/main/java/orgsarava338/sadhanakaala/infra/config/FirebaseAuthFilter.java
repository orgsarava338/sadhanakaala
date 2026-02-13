package orgsarava338.sadhanakaala.infra.config;

import java.io.IOException;
import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.google.firebase.auth.FirebaseToken;

import orgsarava338.sadhanakaala.application.auth.UserService;
import orgsarava338.sadhanakaala.constants.HeaderConstants;
import orgsarava338.sadhanakaala.persistence.entity.UserEntity;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import orgsarava338.sadhanakaala.application.auth.FirebaseAuthUtil;
import orgsarava338.sadhanakaala.constants.ApiConstants;

@Slf4j
@Component
@RequiredArgsConstructor
public class FirebaseAuthFilter extends OncePerRequestFilter {

    private final UserService userService;
    private final FirebaseAuthUtil firebaseAuthUtil;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();
        String header = request.getHeader(HeaderConstants.AUTHORIZATION);

        return path.startsWith(ApiConstants.HEALTH_API) || header == null || !header.startsWith("Bearer ");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        String token = request.getHeader(HeaderConstants.AUTHORIZATION);
        FirebaseToken firebaseToken = firebaseAuthUtil.decodeToken(token);

        UserEntity user = userService.loadOrCreateUser(firebaseToken);
        List<GrantedAuthority> authorities = userService.loadAuthorities(user);

        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(user, null, authorities);
        
        SecurityContextHolder.getContext().setAuthentication(auth);

        chain.doFilter(request, response);
    }
}
