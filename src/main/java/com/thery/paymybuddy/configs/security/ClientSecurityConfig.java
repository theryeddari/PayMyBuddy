package com.thery.paymybuddy.configs.security;

import com.nimbusds.jose.jwk.source.ImmutableSecret;
import com.thery.paymybuddy.model.Client;
import com.thery.paymybuddy.repository.ClientRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.*;

import javax.crypto.spec.SecretKeySpec;
import java.util.Optional;

/**
 * Configuration class for client-related security beans.
 */
@Configuration
public class ClientSecurityConfig {

    private static final Logger logger = LoggerFactory.getLogger(ClientSecurityConfig.class);
    private final ClientRepository clientRepository;

    @Value("${paymybuddy.config.security.security-config.jwt-private-key}")
    private String jwtKey;

    /**
     * Constructor for ClientSecurityConfig.
     *
     * @param clientRepository The repository for accessing client data.
     */
    public ClientSecurityConfig(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    /**
     * Provides a UserDetailsService bean for loading client details by email.
     *
     * @return UserDetailsService implementation lambda function.
     */
    @Bean(name = "clientUserDetailsService")
    public UserDetailsService clientUserDetailsService() {
            //implement loadUserByUsername (interface function) by lambda
            UserDetailsService userDetailsService = clientId -> {
                Long clientIdLong = Long.valueOf(clientId);
                Optional<Client> client = clientRepository.findById(clientIdLong);
            if (client.isEmpty()) {
                throw new UsernameNotFoundException("User not found with id: " + clientId);
            }
            return User.builder()
                    .username(String.valueOf(client.get().getId()))
                    .password(client.get().getPassword()) // Assuming client.getPassword() returns hashed password
                    .roles(client.get().getRole())
                    .build();
        };
            return userDetailsService;
    }

    /**
     * Provides a BCrypt password encoder bean.
     *
     * @return BCryptPasswordEncoder configured password encoder.
     */
    @Bean(name = "clientPasswordEncoder")
    public BCryptPasswordEncoder clientPasswordEncoder() {
        logger.debug("Creating BCrypt password encoder bean");
        return new BCryptPasswordEncoder();
    }

    /**
     * Provides a JWT decoder bean using a secret key.
     *
     * @return JwtDecoder configured with secret key for decoding JWTs.
     */
    @Bean(name = "clientJwtDecoder")
    public JwtDecoder clientJwtDecoder() {
        SecretKeySpec secretKey = new SecretKeySpec(this.jwtKey.getBytes(), 0, this.jwtKey.getBytes().length, "hmacSHA256");
        logger.info("Creating JWT decoder bean with HMAC SHA-256 algorithm");
        return NimbusJwtDecoder.withSecretKey(secretKey).macAlgorithm(MacAlgorithm.HS256).build();
    }

    /**
     * Provides a JWT encoder bean using a secret key.
     *
     * @return JwtEncoder configured with secret key for encoding JWTs.
     */
    @Bean(name = "clientJwtEncoder")
    public JwtEncoder clientJwtEncoder() {
        logger.info("Creating JWT encoder bean with ImmutableSecret key");
        SecretKeySpec secretKey = new SecretKeySpec(this.jwtKey.getBytes(), 0, this.jwtKey.getBytes().length, "hmacSHA256");
        return new NimbusJwtEncoder(new ImmutableSecret<>(secretKey.getEncoded()));
    }
}
