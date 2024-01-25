package com.nazarvalko.lms.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.sql.Timestamp;
import java.util.Date;
import java.util.function.Function;

@Service
public class JwtProvider {

    @Value("${jwt.secret.key}")
    private String secret;

    public String generateToken(UserDetails userDetails) {
        return Jwts.builder()
                .subject(userDetails.getUsername())
                .expiration(new Date(System.currentTimeMillis() + 3 * 60 * 60 * 1000))
                .claim("roles", userDetails.getAuthorities())
                .signWith(getKey())
                .compact();
    }
    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        Claims claim = extractAllClaims(token);
        return claimsResolver.apply(claim);
    }
    private Claims extractAllClaims(String token) {
        return Jwts.parser().verifyWith((SecretKey) getKey()).build().parseSignedClaims(token).getPayload();
    }
    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
    public String[] extractAuthorities(String token) {
        return extractClaim(token, a -> a.get("roles", String[].class));
    }
    public boolean isTokenExpired(String token) {
        return extractExpiration(token).equals(new Date());
    }
    public boolean isTokenValid(String token, UserDetails userDetails) {
        return extractUsername(token).equals(userDetails.getUsername());
    }
    public Key getKey() {
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }


}
