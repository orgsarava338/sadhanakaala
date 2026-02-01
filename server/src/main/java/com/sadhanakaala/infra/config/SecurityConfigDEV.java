package com.sadhanakaala.infra.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

import com.sadhanakaala.constants.ApiConstants;

@Configuration
@Profile({ "local", "dev" })
public class SecurityConfigDEV {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // disable CSRF for dev purposes
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll() // swagger endpoints
                        .requestMatchers(ApiConstants.AUTH_API).permitAll() // authentication endpoints
                        .requestMatchers(ApiConstants.HEALTH_API).permitAll() // health check endpoint

                        .anyRequest().authenticated()) // all other endpoints require authentication

                .httpBasic(basic -> basic.disable()) // disable default basic auth popup
                .formLogin(form -> form.disable()); // disable default form login

        return http.build();
    }
}
