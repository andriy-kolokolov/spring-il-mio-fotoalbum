package com.experis.photoalbum.controller.security;

import com.experis.photoalbum.model.User;
import com.experis.photoalbum.repository.UserRepository;
import com.experis.photoalbum.request.SigninRequest;
import com.experis.photoalbum.request.SignupRequest;
import com.experis.photoalbum.response.ApiResponse;
import com.experis.photoalbum.response.SigninResponse;
import com.experis.photoalbum.security.JwtCore;
import com.experis.photoalbum.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class SecurityController {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private AuthenticationManager authenticationManager;
    private JwtCore jwtCore;
    private UserService userService;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Autowired
    public void setJwtCore(JwtCore jwtCore) {
        this.jwtCore = jwtCore;
    }

    @PostMapping("/signup")
    ResponseEntity<?> signup(@RequestBody SignupRequest signupRequest) {
        try {
            if (userRepository.existsUserByUsername(signupRequest.getUsername())) {
                return ResponseEntity
                        .badRequest()
                        .body(new ApiResponse("Choose a different name", false));
            }
            if (userRepository.existsUserByEmail(signupRequest.getEmail())) {
                return ResponseEntity
                        .badRequest()
                        .body(new ApiResponse("Choose a different email", false));
            }

            userService.registerUser(signupRequest);

            return ResponseEntity
                    .ok(new ApiResponse("User registered successfully", true));

        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(e.getMessage(), false));
        }
    }


    @PostMapping("/signin")
    ResponseEntity<?> signin(@RequestBody SigninRequest signinRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(signinRequest.getUsername(), signinRequest.getPassword())
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtCore.generateToken(authentication);

            return ResponseEntity
                    .ok(new SigninResponse(jwt, "Authentication successful", true));

        } catch (BadCredentialsException e) {
            return ResponseEntity
                    .badRequest()
                    .body(new SigninResponse(null, "Bad credentials", false));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(e.getMessage(), false));
        }
    }
}
