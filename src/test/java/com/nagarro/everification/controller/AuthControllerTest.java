package com.nagarro.everification.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nagarro.everification.payload.request.LoginRequest;
import com.nagarro.everification.payload.request.SignupRequest;
import com.nagarro.everification.service.AuthService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {AuthController.class})
@ExtendWith(SpringExtension.class)
class AuthControllerTest {
    @Autowired
    private AuthController authController;

    @MockBean
    private AuthService authService;

    /**
     * Method under test: {@link AuthController#authenticateUser(LoginRequest)}
     */
    @Test
    void testAuthenticateUser() throws Exception {
        Mockito.<ResponseEntity<?>>when(authService.authenticateUser(Mockito.<LoginRequest>any()))
                .thenReturn(new ResponseEntity<>(HttpStatus.CONTINUE));

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setPassword("passowrd");
        loginRequest.setUsername("janedoe");
        String content = (new ObjectMapper()).writeValueAsString(loginRequest);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/v1/auth/signin")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(authController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status()
                .is(100));
    }

    /**
     * Method under test: {@link AuthController#authenticateUser(LoginRequest)}
     */
    @Test
    void testAuthenticateUser2() throws Exception {
        Mockito.<ResponseEntity<?>>when(authService.authenticateUser(Mockito.<LoginRequest>any()))
                .thenReturn(new ResponseEntity<>(HttpStatus.CONTINUE));

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setPassword("");
        loginRequest.setUsername("janedoe");
        String content = (new ObjectMapper()).writeValueAsString(loginRequest);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/v1/auth/signin")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(authController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status()
                .is(400));
    }

    /**
     * Method under test: {@link AuthController#registerUser(SignupRequest)}
     */
    @Test
    void testRegisterUser() throws Exception {
        Mockito.<ResponseEntity<?>>when(authService.registerUser(Mockito.<SignupRequest>any()))
                .thenReturn(new ResponseEntity<>(HttpStatus.CONTINUE));

        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setBu("Bu");
        signupRequest.setPassword("passowrd");
        signupRequest.setUsername("janedoe");
        String content = (new ObjectMapper()).writeValueAsString(signupRequest);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/v1/auth/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(authController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status()
                .is(100));
    }
}

