package com.ohorodnik.movieland.config.logging;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.security.Principal;
import java.util.UUID;


@Slf4j
@Component
public class LoggerInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler) {
        MDC.put("requestid", UUID.randomUUID().toString());
        Principal principal = request.getUserPrincipal();
        String name = "Anonymous";
        if (principal != null) {
            name = principal.getName();
        }
        MDC.put("login", name);
        return true;
    }
}
