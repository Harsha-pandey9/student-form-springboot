package com.example.demo.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class jwtservice {
private String secretkey="cr709abcdefghijklmnopqrstuvwxyz1234567890ABCDEFGHIJKLMNOPQRSTUVWXYZ";

public jwtservice() {
    // Use a fixed secret key to ensure tokens remain valid across application restarts
    // In production, this should be loaded from environment variables or configuration
}
    public String genetaredtoken(String username) {
        Map<String,Object> claims=new HashMap<>();
        return Jwts.builder()
                .claims()
                .add(claims)
                .subject(username)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis()+60*60*1000*10))
                .and()
                .signWith(getkey())
                .compact();
    }

    private SecretKey getkey() {
        byte[]skey= Decoders.BASE64.decode(secretkey);
        return Keys.hmacShaKeyFor(skey);
    }
        // 🔹 Extract username (subject) from token
        public String extractUserName(String token) {
            return extractClaim(token, Claims::getSubject);
        }

        // 🔹 Extract expiration
        public Date extractExpiration(String token) {
            return extractClaim(token, Claims::getExpiration);
        }

        // 🔹 Generic method to extract claims
        public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
            final Claims claims = extractAllClaims(token);
            return claimsResolver.apply(claims);
        }

        private Claims extractAllClaims(String token) {
            return Jwts.parser()
                    .verifyWith(getkey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        }

        private Boolean isTokenExpired(String token) {
            return extractExpiration(token).before(new Date());
        }

        // 🔹 Validate token
        public boolean validateToken(String token, UserDetails userDetails) {
            final String username = extractUserName(token);
            return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
        }

}
