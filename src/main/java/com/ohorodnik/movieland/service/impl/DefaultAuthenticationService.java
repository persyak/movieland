package com.ohorodnik.movieland.service.impl;

import com.ohorodnik.movieland.entity.User;
import com.ohorodnik.movieland.repository.UserRepository;
import com.ohorodnik.movieland.security.utils.JwtUtils;
import com.ohorodnik.movieland.service.AuthenticationService;
import com.ohorodnik.movieland.web.controller.request.AuthenticationRequest;
import com.ohorodnik.movieland.web.controller.request.LoginRequest;
import com.ohorodnik.movieland.web.controller.response.AuthenticationResponse;
import com.ohorodnik.movieland.web.controller.response.LogoutResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class DefaultAuthenticationService implements AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;

    @Override
    public AuthenticationResponse register(AuthenticationRequest registerRequest) {
        User user = User.builder()
                .nickname(registerRequest.getNickname())
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()).getBytes())
                .type(registerRequest.getType())
                .build();

        userRepository.save(user);

        UUID jwtToken = jwtUtils.generateToken(user);
        log.info("User {} is authenticated successfully", user.getEmail());
        return AuthenticationResponse.builder().uuid(jwtToken).build();
    }

    @Override
    public AuthenticationResponse login(LoginRequest request) {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                request.getEmail(), request.getPassword());
        try {
            Authentication authenticate = authenticationManager.authenticate(token);
            User user = (User) authenticate.getPrincipal();
            UUID uuidToken = jwtUtils.generateToken(user);
            log.info("Successful login for {}", user.getEmail());
            return AuthenticationResponse.builder().uuid(uuidToken).nickname(user.getNickname()).build();
        } catch (AuthenticationException e) {
            return AuthenticationResponse.builder().build();
        }
    }

    @Override
    public LogoutResponse logout(UUID uuid) {
        Optional<UUID> uuidOptional = jwtUtils.deleteToken(uuid);
        if (uuidOptional.isPresent()) {
            log.info("User has been logged out");
            return LogoutResponse.builder().message("Logged out").build();
        }
        return LogoutResponse.builder().message("No session found").build();
    }

}
