package com.ohorodnik.movieland.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserDetailsServiceExtention extends UserDetailsService {

    UserDetails findUserById(Integer id);
}
