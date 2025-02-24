package com.ohorodnik.movieland.security.utils;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

@Component
public class JwtUtils {

    private static final String SECRET_KEY = "bDwrN0eHXPb/9Yd4bOgGJ7uClWzUovmq3Qv97R6D4C8byI+mo7bL04RUHAQBzAD5";

    Cache<UUID, String> cache = Caffeine.newBuilder()
            .expireAfterWrite(120, TimeUnit.MINUTES)
            .initialCapacity(10)
            .build();

    public String extractUsername(UUID uuid) {
        return extractClaim(Claims::getSubject, uuid);
    }

    public <T> T extractClaim(Function<Claims, T> claimsResolver, UUID uuid) {
        final Claims claims = extractAllClaims(uuid);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(UUID uuid) {
        return Jwts.parser().verifyWith(getsignInKey()).build().parseSignedClaims(cache.getIfPresent(uuid)).getPayload();
    }

    private SecretKey getsignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public boolean isTokenValid(UUID uuid, UserDetails userDetails) {
        final String username = extractUsername(uuid);
        return (username.equals(userDetails.getUsername()));
    }

    public UUID generateToken(UserDetails userDetails) {
        UUID uuid = UUID.randomUUID();
        cache.put(uuid, createToken(new HashMap<>(), userDetails));
        return uuid;
    }

    public Optional<UUID> deleteToken(UUID uuid) {
        if (cache.getIfPresent(uuid) != null) {
            cache.invalidate(uuid);
            return Optional.of(uuid);
        }
        return Optional.empty();
    }

    private String createToken(Map<String, Object> claims, UserDetails userDetails) {
        return Jwts.builder()
                .claims(claims)
                .subject(userDetails.getUsername())
                .claim("authorities", userDetails.getAuthorities())
                .signWith(getsignInKey())
                .compact();
    }
}
