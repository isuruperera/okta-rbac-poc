package com.trustpayments.okta.poc.oktarbacpoc.util;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Component
@ConfigurationProperties(prefix = "permissions")
public class EndpointSecurityRules {

    public record EndpointSecurityRule(String[] endpoints, String[] permissions, boolean permitAll) {
    }

    List<EndpointSecurityRule> endpointSecurityRules;

}
