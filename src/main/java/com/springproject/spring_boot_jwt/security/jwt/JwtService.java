package com.springproject.spring_boot_jwt.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class JwtService {


    private static final String secretKey =
            "cTlZN3ZLM3hUMmJXOHBMNXNEMWZHNmhKNG1OMGNSetVWZUEyeVh3QzdWblFrUDhvSA==";

    private static final long accessExpiration = 1000 * 60 * 60;
    private static final long refreshExpiration = 1000 * 60 * 60 * 24 * 7;

    // ================================
    // EXTRACT DATA FROM TOKEN
    // ================================

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {

        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> resolver) {
        final Claims claims = extractAllClaims(token);
        return resolver.apply(claims);
    }

    // ================================
    // GENERATE TOKEN
    // ================================

    public String generateAccessToken(UserDetails userDetails) {

        return buildToken(Map.of(), userDetails,accessExpiration);
    }
    public String generateRefreshToken(UserDetails userDetails){
        return buildToken(Map.of(), userDetails,refreshExpiration);
    }

    public String buildToken(
            Map<String, Object> extraClaims,
            UserDetails userDetails,
            long expiration
    ) {
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSigInKey())
                .compact();
    }

    // ================================
    // VALIDATE TOKEN
    // ================================

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);

        return username.equals(userDetails.getUsername())
                && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // ================================
    // INTERNAL METHODS
    // ================================

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSigInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}