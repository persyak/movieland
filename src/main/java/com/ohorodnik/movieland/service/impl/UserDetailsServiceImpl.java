package com.ohorodnik.movieland.service.impl;

import com.ohorodnik.movieland.entity.User;
import com.ohorodnik.movieland.repository.UserRepository;
import com.ohorodnik.movieland.service.UserDetailsServiceExtention;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsServiceExtention {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> userOptional = userRepository.findByEmailIgnoreCase(email);

        if (userOptional.isEmpty()) {
            throw new UsernameNotFoundException("User does not exist");
        }
        return userOptional.get();
    }

    public UserDetails findUserById(Integer id) {
        Optional<User> userOptional = userRepository.findById(id);
        return userOptional.orElseThrow(() -> new UsernameNotFoundException("User does not exist"));
    }
}
