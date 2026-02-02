package orgsarava338.sadhanakaala.infra.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

import orgsarava338.sadhanakaala.constants.ApiConstants;

@Configuration
@Profile("prod")
public class SecurityConfigProd {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(ApiConstants.AUTH_API).permitAll() // authentication endpoints
                        .requestMatchers(ApiConstants.HEALTH_API).permitAll() // health check endpoint

                        .anyRequest().authenticated());

        return http.build();
    }
}
