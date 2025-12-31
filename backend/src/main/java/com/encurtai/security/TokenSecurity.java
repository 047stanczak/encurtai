package com.encurtai.security;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.encurtai.models.User;

@Service
public class TokenSecurity {
    @Value("${JWT_SECRET_KEY}")
    private String secret;

    public String generateToken(User user){
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            String token = JWT.create()
                .withIssuer("encurtai")
                .withSubject(user.getEmail())
                .withClaim("id", user.getId())
                .withExpiresAt(generateExpirationDate())
                .sign(algorithm);
                return token;
        } catch (JWTCreationException e) {
            throw new RuntimeException("Erro ao gerar token de autenticação" + e);
        }
    }

    public String validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                .withIssuer("encurtai")
                .build()
                .verify(token)
                .getSubject();
        } catch (JWTVerificationException exception) {
            return null;
        }
    }

    private Instant generateExpirationDate() {
        return LocalDateTime.now().plusHours(1).toInstant(ZoneOffset.of("-03:00"));
    }
}
