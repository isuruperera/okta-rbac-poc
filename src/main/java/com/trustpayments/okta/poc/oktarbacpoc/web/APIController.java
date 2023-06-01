package com.trustpayments.okta.poc.oktarbacpoc.web;


import com.trustpayments.okta.poc.oktarbacpoc.model.Message;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Handles requests to "/api" endpoints.
 */
@RestController
@RequestMapping(path = "api", produces = MediaType.APPLICATION_JSON_VALUE)
// For simplicity of this sample, allow all origins. Real applications should configure CORS for their use case.
@CrossOrigin(origins = "*")
public class APIController {

    @GetMapping(value = "/public")
    public Message publicEndpoint() {
        return new Message("NO need to be authenticated to call /api/public.");
    }

    @GetMapping(value = "/private")
    public Message privateEndpoint() {
        return new Message("You can access PRIVATE endpoint because you are Authenticated.");
    }

    @GetMapping(value = "/private-manager")
    public Message privateManagerEndpoint() {
        return new Message("You can access MANAGER endpoint because you are Authenticated with a Token granted the 'role:manager' scope");
    }

    @GetMapping(value = "/private-developer")
    public Message privateExecutiveEndpoint() {
        return new Message("You can access DEVELOPER endpoint because you are Authenticated with a Token granted the 'role:developer' scope");
    }

    @GetMapping(value = "/private-admin")
    public Message privateAdminEndpoint() {
        return new Message("You can access EXECUTIVE AND MANAGER endpoints because you are Authenticated with a Token granted the 'role:admin' scope");
    }

    @GetMapping(value = "/private-config")
    public Message privateConfigEndpoint() {
        return new Message("You can perform CONFIGURATIONS because you are Authenticated with a Token granted the 'role:admin' scope");
    }
}
