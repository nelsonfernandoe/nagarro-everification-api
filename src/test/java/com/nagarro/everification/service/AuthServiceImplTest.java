package com.nagarro.everification.service;

import com.nagarro.everification.exception.EverificationException;
import com.nagarro.everification.model.User;
import com.nagarro.everification.payload.request.LoginRequest;
import com.nagarro.everification.payload.request.SignupRequest;
import com.nagarro.everification.payload.response.MessageResponse;
import com.nagarro.everification.payload.response.UserInfoResponse;
import com.nagarro.everification.repository.UserRepository;
import com.nagarro.everification.security.jwt.JwtUtils;
import com.nagarro.everification.security.services.UserDetailsImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = {AuthServiceImpl.class})
@ExtendWith(SpringExtension.class)
class AuthServiceImplTest {
    @Autowired
    private AuthServiceImpl authServiceImpl;

    @MockBean
    private AuthenticationManager authenticationManager;

    @MockBean
    private JwtUtils jwtUtils;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @MockBean
    private UserRepository userRepository;

    /**
     * Method under test: {@link AuthServiceImpl#authenticateUser(LoginRequest)}
     */
    @Test
    void testAuthenticateUser() throws AuthenticationException, EverificationException {
        ResponseCookie cookie = ResponseCookie.from("jwtCookie", "dummyToken")
                .path("/api")
                .maxAge(24 * 60 * 60)
                .httpOnly(true)
                .build();

        when(jwtUtils.generateJwtCookie(Mockito.<UserDetailsImpl>any())).thenReturn(cookie);
        when(authenticationManager.authenticate(Mockito.<Authentication>any())).thenReturn(new TestingAuthenticationToken(new UserDetailsImpl(1L, "janedoe", "Bu", "password"), "Credentials"));

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setPassword("password");
        loginRequest.setUsername("janedoe");
        ResponseEntity<UserInfoResponse> responseEntity = authServiceImpl.authenticateUser(loginRequest);
        assertEquals(1L, responseEntity.getBody()
                .id());
    }

    /**
     * Method under test: {@link AuthServiceImpl#registerUser(SignupRequest)}
     */
    @Test
    void testRegisterUser() throws EverificationException {
        when(userRepository.existsByUsername(Mockito.<String>any())).thenReturn(true);

        SignupRequest signUpRequest = new SignupRequest();
        signUpRequest.setBu("Bu");
        signUpRequest.setPassword("password");
        signUpRequest.setUsername("janedoe");
        ResponseEntity<?> actualRegisterUserResult = authServiceImpl.registerUser(signUpRequest);
        assertTrue(actualRegisterUserResult.hasBody());
        assertTrue(actualRegisterUserResult.getHeaders()
                .isEmpty());
        assertEquals(HttpStatus.BAD_REQUEST, actualRegisterUserResult.getStatusCode());
        assertEquals("Error: Username is already taken!", ((MessageResponse) actualRegisterUserResult.getBody()).message());
        verify(userRepository).existsByUsername(Mockito.<String>any());
    }

    /**
     * Method under test: {@link AuthServiceImpl#registerUser(SignupRequest)}
     */
    @Test
    void testRegisterUser2() throws EverificationException {
        User user = new User();
        user.setBu("Bu");
        user.setId(1L);
        user.setPassword("password");
        user.setUsername("janedoe");
        when(userRepository.existsByUsername(Mockito.<String>any())).thenReturn(false);
        when(userRepository.save(Mockito.<User>any())).thenReturn(user);
        when(passwordEncoder.encode(Mockito.<CharSequence>any())).thenReturn("secret");

        SignupRequest signUpRequest = new SignupRequest();
        signUpRequest.setBu("Bu");
        signUpRequest.setPassword("password");
        signUpRequest.setUsername("janedoe");
        ResponseEntity<?> actualRegisterUserResult = authServiceImpl.registerUser(signUpRequest);
        assertTrue(actualRegisterUserResult.hasBody());
        assertTrue(actualRegisterUserResult.getHeaders()
                .isEmpty());
        assertEquals(HttpStatus.OK, actualRegisterUserResult.getStatusCode());
        assertEquals("User registered successfully!", ((MessageResponse) actualRegisterUserResult.getBody()).message());
        verify(userRepository).existsByUsername(Mockito.<String>any());
        verify(userRepository).save(Mockito.<User>any());
        verify(passwordEncoder).encode(Mockito.<CharSequence>any());
    }

    /**
     * Method under test: {@link AuthServiceImpl#logoutUser()}
     */
    @Test
    void testLogoutUser() throws EverificationException {
        ResponseCookie cookie = ResponseCookie.from("jwtCookie", null)
                .path("/api")
                .build();
        when(jwtUtils.getCleanJwtCookie()).thenReturn(cookie);
        ResponseEntity<?> actualLogoutResult = authServiceImpl.logoutUser();
        assertEquals(HttpStatus.OK, actualLogoutResult.getStatusCode());
        assertEquals(cookie.toString(),
                actualLogoutResult.getHeaders()
                        .get(HttpHeaders.SET_COOKIE)
                        .get(0));
    }
}

