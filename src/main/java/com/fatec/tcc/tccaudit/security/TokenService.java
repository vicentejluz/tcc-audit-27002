package com.fatec.tcc.tccaudit.security;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.fatec.tcc.tccaudit.models.entities.Employee;

@Service
public class TokenService {
    @Value("${api_token_secret}")
    private String secret;

    public String generateToken(Employee employee) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.create().withIssuer("TCC AUDIT 27002")
                    .withClaim("id", employee.getIdEmployee())
                    .withClaim("name", employee.getName())
                    .withSubject(employee.getEmail())
                    .withClaim("idCompany", employee.getCompany().getIdCompany())
                    .withClaim("company", employee.getCompany().getName())
                    .withClaim("employeeRole", employee.getEmployeeRole().name())
                    .withIssuedAt(Instant.now())
                    .withExpiresAt(expirationDate()).sign(algorithm);
        } catch (JWTCreationException exception) {
            throw new RuntimeException("Error generating JWT token: ", exception);
        }
    }

    public String getSubject(String tokenJWT) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm).withIssuer("TCC AUDIT 27002").build().verify(tokenJWT).getSubject();
        } catch (JWTVerificationException exception) {
            throw new RuntimeException("JWT Token invalid or expired!");
        }
    }

    private Instant expirationDate() {
        return LocalDateTime.now().plusDays(1).toInstant(ZoneOffset.of("-03:00"));
        // return LocalDateTime.now().plusMinutes(1).toInstant(ZoneOffset.of("-03:00"));
    }
}