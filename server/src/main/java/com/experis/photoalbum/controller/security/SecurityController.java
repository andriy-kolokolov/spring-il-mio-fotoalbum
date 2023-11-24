package com.experis.photoalbum.controller.security;

import com.experis.photoalbum.dto.UserDTO;
import com.experis.photoalbum.model.User;
import com.experis.photoalbum.repository.UserRepository;
import com.experis.photoalbum.request.SigninRequest;
import com.experis.photoalbum.request.SignupRequest;
import com.experis.photoalbum.response.ApiResponse;
import com.experis.photoalbum.response.SigninResponse;
import com.experis.photoalbum.security.JwtCore;
import com.experis.photoalbum.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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

import java.util.Optional;

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

    @PostMapping(value = "/signup")
    ResponseEntity<?> signup(@Valid @RequestBody SignupRequest signupRequest) {

        userService.registerUser(signupRequest);

        return ResponseEntity
                .ok(new ApiResponse("User registered successfully", true));
    }


    @PostMapping("/signin")
    ResponseEntity<?> signin(@RequestBody SigninRequest signinRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(signinRequest.getUsername(), signinRequest.getPassword())
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtCore.generateToken(authentication);

            Optional<User> optionalUser = userRepository.findByUsername(signinRequest.getUsername());

            if (optionalUser.isEmpty()) {
                return ResponseEntity
                        .status(HttpStatus.UNAUTHORIZED)
                        .body(new SigninResponse(null, "User not found", false, null));
            }

            User user = optionalUser.get();
            UserDTO userDTO = UserDTO.fromUser(user);

            return ResponseEntity
                    .ok(new SigninResponse(jwt, "Authentication successful", true, userDTO));

        } catch (BadCredentialsException e) {
            return ResponseEntity
                    .badRequest()
                    .body(new SigninResponse(null, "Bad credentials", false, null));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(e.getMessage(), false));
        }
    }
}
