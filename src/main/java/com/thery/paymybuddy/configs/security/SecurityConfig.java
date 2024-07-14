package com.thery.paymybuddy.configs.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Configuration class for setting up security filters and policies.
 */
@Configuration
public class SecurityConfig {

    private static final Logger logger = LoggerFactory.getLogger(SecurityConfig.class);

    /**
     * Configures security filters for HTTP requests.
     *
     * @param http HttpSecurity object to configure security settings
     * @return SecurityFilterChain configured filter chain
     * @throws Exception If configuration fails
     */
    @Bean(name = "clientSecurity")
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        logger.info("Configuring security filters for HTTP requests");

        // Disable CSRF protection as we are using JWT tokens
        // Configure session management to be stateless
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // Configure OAuth2 resource server to use JWT tokens with default settings
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()))

                // Define authorization rules for specific HTTP endpoints
                .authorizeHttpRequests(auth ->
                        auth
                                .requestMatchers("/api/fr/auth/**").permitAll() // Allow all requests to authentication endpoints
                                .requestMatchers("/api/fr/client/**").hasRole("CLIENT") // Allow requests with role CLIENT to client endpoints
                                .anyRequest().denyAll() // Deny any other requests
                )
                .build();
    }

}

