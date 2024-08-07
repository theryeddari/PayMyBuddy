package com.thery.paymybuddy.configs.security;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Set;
import java.util.stream.Collectors;

import static com.thery.paymybuddy.exception.JwtClientServiceConfigException.GenerateTokenConfigExceptionClient;

/**
 * Service class for handling JWT token operations.
 */
@Configuration
public class JwtClientServiceConfig {

    private static final Logger logger = LogManager.getLogger(JwtClientServiceConfig.class);
    private final JwtEncoder jwtEncoder;
    @Value("${spring.security.oauth2.resourceserver.jwt.issuer-uri}")
    private String serverUrl;

    /**
     * Constructor for JwtClientServiceConfig.
     *
     * @param jwtEncoder the JWT encoder
     */
    public JwtClientServiceConfig(@Qualifier("clientJwtEncoder") JwtEncoder jwtEncoder) {
        this.jwtEncoder = jwtEncoder;
    }

    /**
     * Generates a JWT token for the authenticated user.
     *
     * @param authentication the authentication object containing user details
     * @return the generated JWT token as a string
     */
    public String generateToken(Authentication authentication) throws GenerateTokenConfigExceptionClient {
        try {
            String nameClient = authentication.getName();

            logger.debug("Generating token for user: {}", nameClient);

            Set<String> roles = authentication.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toSet());
            logger.debug("User roles: {}", roles);

            Instant now = Instant.now();
            JwtClaimsSet claims = JwtClaimsSet.builder()
                    .issuer(serverUrl)
                    .issuedAt(now)
                    .expiresAt(now.plus(1, ChronoUnit.DAYS))
                    .subject(nameClient)
                    .claim("roles", roles)
                    .build();

            JwtEncoderParameters jwtEncoderParameters = JwtEncoderParameters.from(JwsHeader.with(MacAlgorithm.HS256).build(), claims);
            String token = this.jwtEncoder.encode(jwtEncoderParameters).getTokenValue();
            logger.info("Generated JWT token for user: {}", nameClient);

            return token;
        } catch (Exception e) {
            throw new GenerateTokenConfigExceptionClient(e);
        }
    }
}
