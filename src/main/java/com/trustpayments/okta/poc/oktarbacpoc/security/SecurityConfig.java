package com.trustpayments.okta.poc.oktarbacpoc.security;


import com.trustpayments.okta.poc.oktarbacpoc.util.EndpointSecurityRules;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Configures our application with Spring Security to restrict access to our API endpoints.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {


    @Value("${auth0.audience}")
    private String audience;

    @Value("${spring.security.oauth2.resourceserver.jwt.issuer-uri}")
    private String issuer;


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, EndpointSecurityRules endpointSecurityRules) throws Exception {
        http.authorizeHttpRequests(customizer -> endpointSecurityRules.getEndpointSecurityRules().forEach(rule -> {
                            if (rule.permitAll()) {
                                customizer.requestMatchers(rule.endpoints()).permitAll();
                            } else if (rule.permissions() == null || rule.permissions().length == 0) {
                                customizer.requestMatchers(rule.endpoints()).authenticated();
                            } else {
                                customizer.requestMatchers(rule.endpoints()).hasAnyAuthority(rule.permissions());
                            }
                        })
                )
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt.decoder(jwtDecoder()))
                );
        return http.build();
    }

    JwtDecoder jwtDecoder() {
        /*
        By default, Spring Security does not validate the "aud" claim of the token, to ensure that this token is
        indeed intended for our app. Adding our own validator is easy to do:
        */

        NimbusJwtDecoder jwtDecoder = JwtDecoders.fromOidcIssuerLocation(issuer);

        OAuth2TokenValidator<Jwt> audienceValidator = new AudienceValidator(audience);
        OAuth2TokenValidator<Jwt> withIssuer = JwtValidators.createDefaultWithIssuer(issuer);
        OAuth2TokenValidator<Jwt> withAudience = new DelegatingOAuth2TokenValidator<>(withIssuer, audienceValidator);

        jwtDecoder.setJwtValidator(withAudience);

        return jwtDecoder;
    }
}
