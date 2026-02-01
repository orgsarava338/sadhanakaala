package com.sadhanakaala.infra.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@Profile({ "local", "dev" })
public class SecurityConfigDEV {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // disable CSRF for dev purposes
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**")
                        .permitAll() // swagger endpoints

                        .requestMatchers("/auth/**").permitAll() // authentication endpoints

                        .anyRequest().authenticated()) // all other endpoints require
                                                       // authentication
                .httpBasic(basic -> basic.disable()) // disable default basic auth popup
                .formLogin(form -> form.disable()); // disable default form login

        return http.build();
    }
}
