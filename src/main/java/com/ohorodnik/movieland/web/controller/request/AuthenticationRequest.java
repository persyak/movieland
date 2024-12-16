package com.ohorodnik.movieland.web.controller.request;

import com.ohorodnik.movieland.utils.enums.Type;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationRequest {
    private String nickname;
    private String email;
    private String password;
    private Type type;
}
