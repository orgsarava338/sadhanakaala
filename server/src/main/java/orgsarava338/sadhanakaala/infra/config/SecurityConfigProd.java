package orgsarava338.sadhanakaala.infra.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
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
import orgsarava338.sadhanakaala.constants.HeaderConstants;

@Configuration
@Profile("prod")
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfigProd {

    private final FirebaseAuthFilter firebaseAuthFilter;

    @Bean
    CorsConfigurationSource configurationSource() {
        CorsConfiguration config = new CorsConfiguration();

        config.setAllowedOrigins(List.of());
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of(HeaderConstants.AUTHORIZATION));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return source;
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .cors(Customizer.withDefaults()) // enable cors
                .csrf(csrf -> csrf.disable()) // disable csrf
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(ApiConstants.AUTH_API).permitAll() // authentication endpoints
                        .requestMatchers(ApiConstants.HEALTH_API).permitAll() // health check endpoint

                        .anyRequest().authenticated())

                .addFilterBefore(firebaseAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
