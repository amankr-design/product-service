package com.aman.ecommerce.productservice.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;

import java.util.Date;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expiration;

    private Key getSigningKey(){

        return Keys.hmacShaKeyFor(secret.getBytes());
    }

    public String generateToken(String email){

        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String extractEmail(String token) {

        return extractAllClaims(token).getSubject();
    }

    private Claims extractAllClaims(String token){

        return Jwts.parserBuilder().setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean isTokenValid(String  token, String email){

        return extractEmail(token).equals(email) && ! isTokenExpired(token);
    }

    private boolean isTokenExpired(String token){

        return extractAllClaims(token)
                .getExpiration()
                .before(new Date());
    }

}
