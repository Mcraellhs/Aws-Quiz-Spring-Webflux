package com.example.QuizApp.jwt;

import com.example.QuizApp.security.ApplicationUserRole;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Objects;

@Service
public class JwtService {
    private final SecretKey key;
    private final JwtParser parser;

    public JwtService() {
        this.key = Keys.hmacShaKeyFor("sdfASddf23214!#$HAFgfh56754623sdf=)(".getBytes());
        this.parser = Jwts.parserBuilder().setSigningKey(this.key).build();
    }


    // ret jwt token based on username

    public String generate(String userName, ApplicationUserRole role){
        JwtBuilder builder = Jwts.builder()
                .setSubject(userName)
                .claim("role", role.name())
                .setIssuedAt(Date.from(Instant.now()))
                .setExpiration(Date.from(Instant.now().plus(35, ChronoUnit.MINUTES)))
                .signWith(key);
        return builder.compact();
    }

    public String getUserName(String token){
        Claims claims = parser.parseClaimsJws(token).getBody();
        return claims.getSubject();
    }

    public boolean validate(UserDetails user,String token){
        Claims claims = parser.parseClaimsJws(token).getBody();

        boolean unexpired = claims.getExpiration().after(Date.from(Instant.now()));

        return unexpired && Objects.equals(user.getUsername(), claims.getSubject());
    }

}