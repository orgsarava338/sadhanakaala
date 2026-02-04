package orgsarava338.sadhanakaala.infra.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import lombok.RequiredArgsConstructor;
import orgsarava338.sadhanakaala.constants.ApiConstants;

@Configuration
@Profile({ "local", "dev" })
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfigDEV {

    private final FirebaseAuthFilter firebaseAuthFilter;

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();

        config.setAllowedOrigins(List.of(
                "http://localhost:5173", "http://127.0.0.1:5173" // web UI local
        ));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return source;
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(Customizer.withDefaults()) // enable cors
                .csrf(csrf -> csrf.disable()) // disable CSRF for dev purposes
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll() // swagger endpoints
                        .requestMatchers(ApiConstants.AUTH_API + "/**").permitAll() // authentication endpoints
                        .requestMatchers(ApiConstants.HEALTH_API + "/**").permitAll() // health check endpoint
                        .anyRequest().authenticated()) // all other endpoints require authentication
                .httpBasic(basic -> basic.disable()) // disable default basic auth popup
                .formLogin(form -> form.disable()) // disable default form login

                .addFilterBefore(firebaseAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
