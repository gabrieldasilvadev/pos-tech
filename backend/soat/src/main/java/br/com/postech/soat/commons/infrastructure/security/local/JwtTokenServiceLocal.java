package br.com.postech.soat.commons.infrastructure.security.local;

import br.com.postech.soat.commons.infrastructure.security.SecurityProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.time.Clock;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.crypto.SecretKey;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile({"local", "test"})
@RequiredArgsConstructor
public class JwtTokenServiceLocal {

    private final SecurityProperties securityProperties;
    private final Clock clock;

    private SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(securityProperties.getJwt().getSecret().getBytes());
    }

    public String generateToken(String subject, List<String> roles) {
        Instant now = Instant.now(clock);
        Instant expiration = now.plus(securityProperties.getJwt().getExpiration());

        return Jwts.builder()
            .subject(subject)
            .claim("roles", roles)
            .audience().add(securityProperties.getJwt().getAudience()).and()
            .issuedAt(Date.from(now))
            .expiration(Date.from(expiration))
            .signWith(getSecretKey(), Jwts.SIG.HS256)
            .compact();
    }

    public boolean isTokenValid(String token) {
        try {
            Jwts.parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }

    public Optional<String> extractSubject(String token) {
        try {
            Claims claims = Jwts.parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
            return Optional.ofNullable(claims.getSubject());
        } catch (JwtException e) {
            return Optional.empty();
        }
    }
}
