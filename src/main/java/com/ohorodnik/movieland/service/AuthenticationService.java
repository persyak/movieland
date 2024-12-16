package com.ohorodnik.movieland.service;

import com.ohorodnik.movieland.web.controller.request.AuthenticationRequest;
import com.ohorodnik.movieland.web.controller.request.LoginRequest;
import com.ohorodnik.movieland.web.controller.response.AuthenticationResponse;
import com.ohorodnik.movieland.web.controller.response.LogoutResponse;

import java.util.UUID;

public interface AuthenticationService {

    AuthenticationResponse register(AuthenticationRequest registerRequest);

    AuthenticationResponse login(LoginRequest request);

    LogoutResponse logout(UUID uuid);
}
