package com.blocks.movies.util;

import com.blocks.movies.model.MovieUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;

@Service
public class JwtUtil {
    @Value("${movies.app.jwt.secret}")
    private String SECRET_KEY;

    @Value("${movies.app.jwt.expiration}")
    private Long expiration;

    public String extractUserId(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public String generateToken(MovieUser movieUser) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, movieUser.getId());
    }

    private String createToken(Map<String, Object> claims, UUID userId) {
        System.out.println(new Date(System.currentTimeMillis() + expiration));
        return Jwts.builder().setClaims(claims).setSubject(userId.toString()).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
    }

    public Boolean validateToken(String token, MovieUser movieUser) {
        final String userId = extractUserId(token);
        return (userId.equals(movieUser.getId().toString()) && !isTokenExpired(token));
    }
}
