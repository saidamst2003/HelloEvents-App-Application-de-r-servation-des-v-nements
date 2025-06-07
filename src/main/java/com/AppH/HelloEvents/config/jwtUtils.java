package com.AppH.HelloEvents.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class jwtUtils {

    @Value("${app.secret-key}")
    private String secretKey;

    @Value("${app.expiration-time}")
    private Long expirationTime;
    public String encodeToBase64(byte[] data) {
        return Base64.getEncoder().encodeToString(data);
    }

    public byte[] decodeFromBase64(String base64String) {
        return Base64.getDecoder().decode(base64String);
    }
    public String generateToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        String token = createToken(claims, username);

        System.out.println("Generated token for [" + username + "]: " + token);

        return token;
    }

    private String createToken(Map<String, Object> claims, String subject) {
        Date now = new Date();
        Date expiration = new Date(System.currentTimeMillis() + expirationTime);

        System.out.println("Issued At: " + now);
        System.out.println("Expiration At: " + expiration);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(SignatureAlgorithm.HS256, getSignKey())
                .compact();
    }

    private Key getSignKey() {
        System.out.println(" SecretKey being used: " + secretKey);
        byte[] keyBytes = secretKey.getBytes();
        return new SecretKeySpec(keyBytes, SignatureAlgorithm.HS256.getJcaName());
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return extractExpirationDate(token).before(new Date());
    }

    private Date extractExpirationDate(String token) {
        return extiratClaim(token, Claims::getExpiration);
    }

    public String extractUsername(String token) {
        return extiratClaim(token, Claims::getSubject);
    }

    private <T> T extiratClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extiratAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extiratAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(getSignKey())
                .parseClaimsJws(token)
                .getBody();
    }
}
