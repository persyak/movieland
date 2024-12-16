package com.ohorodnik.movieland.web.controller;

import com.ohorodnik.movieland.service.AuthenticationService;
import com.ohorodnik.movieland.web.controller.request.AuthenticationRequest;
import com.ohorodnik.movieland.web.controller.request.LoginRequest;
import com.ohorodnik.movieland.web.controller.response.AuthenticationResponse;
import com.ohorodnik.movieland.web.controller.response.LogoutResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    protected ResponseEntity<AuthenticationResponse> register(
            @RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(authenticationService.register(request));
    }

    @PostMapping("/login")
    protected ResponseEntity<AuthenticationResponse> login(
            @RequestBody LoginRequest request) {
        AuthenticationResponse authenticationResponse = authenticationService.login(request);
        if (authenticationResponse.getUuid() != null) {
            return ResponseEntity.ok(authenticationResponse);
        }
        return ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/logout")
    protected ResponseEntity<LogoutResponse> logout(@RequestHeader UUID uuid) {
        return ResponseEntity.ok(authenticationService.logout(uuid));
    }
}
