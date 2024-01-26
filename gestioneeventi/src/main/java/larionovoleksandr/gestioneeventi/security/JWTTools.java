package larionovoleksandr.gestioneeventi.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import larionovoleksandr.gestioneeventi.entities.User;
import larionovoleksandr.gestioneeventi.exceptions.UnauthorizedException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
@Component
public class JWTTools {
    @Value("${spring.jwt.secret}")
    private String secret;

    public String createToken(User user) {
        return Jwts.builder().subject(String.valueOf(user.getId()))
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24))
                .signWith(Keys.hmacShaKeyFor(secret.getBytes())) // Firmo il token
                .compact();
    }

    public void verifyToken(String token) { // Dato un token mi lancia eccezioni in caso di token manipolato/scaduto
        try {
            Jwts.parser().verifyWith(Keys.hmacShaKeyFor(secret.getBytes())).build().parse(token);
        } catch (Exception ex) {
            throw new UnauthorizedException("Problemi col token! Per favore effettua di nuovo il login!");
        }
    }

    public String extractIdFromToken(String token) {
        return Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(secret.getBytes()))
                .build()
                .parseSignedClaims(token).getPayload().getSubject();
    }
}
