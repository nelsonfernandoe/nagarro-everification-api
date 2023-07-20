package com.nagarro.everification.service;

import com.nagarro.everification.exception.EverificationException;
import com.nagarro.everification.payload.request.LoginRequest;
import com.nagarro.everification.payload.request.SignupRequest;
import org.springframework.http.ResponseEntity;

public interface AuthService {
    ResponseEntity<?> authenticateUser(LoginRequest loginRequest) throws EverificationException;

    ResponseEntity<?> registerUser(SignupRequest signUpRequest) throws EverificationException;

    ResponseEntity<?> logoutUser() throws EverificationException;
}
