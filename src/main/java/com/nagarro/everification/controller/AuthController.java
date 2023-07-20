package com.nagarro.everification.controller;

import com.nagarro.everification.exception.EverificationException;
import com.nagarro.everification.payload.request.LoginRequest;
import com.nagarro.everification.payload.request.SignupRequest;
import com.nagarro.everification.service.AuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(originPatterns = "*", maxAge = 3600, allowCredentials = "true")
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    private static final Logger LOG = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private AuthService authService;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) throws EverificationException {
        LOG.info("Login requested for user: {}", loginRequest.getUsername());
        return authService.authenticateUser(loginRequest);
    }

    @PostMapping(value = "/signup", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) throws EverificationException {
        LOG.info("Registration requested for user: {}", signUpRequest.getUsername());
        return authService.registerUser(signUpRequest);
    }

    @PostMapping("/signout")
    public ResponseEntity<?> logoutUser() throws EverificationException {
        LOG.info("Sign out requested.");
        return authService.logoutUser();
    }
}
