package br.com.postech.soat.commons.infrastructure.security;

import com.nimbusds.jose.JOSEObjectType;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.time.Clock;
import java.time.Instant;
import java.util.Date;
import java.util.Optional;
import javax.crypto.SecretKey;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class JwtTokenService {

    private final RsaKeyProvider keyProvider;
    private final SecurityProperties securityProperties;
    private final Clock clock;
    private SecretKey secretKey;

    @PostConstruct
    void init() {
        final String secret = securityProperties.getJwt().getSecret();
        if (!StringUtils.hasText(secret) || secret.length() < 32) {
            throw new IllegalStateException("JWT secret must be configured with at least 32 characters");
        }
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    public String generateToken(String subject) {
        try {
            Instant now = Instant.now(clock);
            Instant expiration = now.plus(securityProperties.getJwt().getExpiration());

            JWTClaimsSet claims = new JWTClaimsSet.Builder()
                .subject(subject)
                .issuer("fast-food-tech")
                .audience(securityProperties.getJwt().getAudience())
                .issueTime(Date.from(now))
                .expirationTime(Date.from(expiration))
                .build();

            String algorithm = securityProperties.getJwt().getAlgorithm();

            if ("HS256".equalsIgnoreCase(algorithm)) {
                SecretKey secretKey = Keys.hmacShaKeyFor(securityProperties.getJwt().getSecret().getBytes(StandardCharsets.UTF_8));
                JWSHeader header = new JWSHeader.Builder(JWSAlgorithm.HS256).type(JOSEObjectType.JWT).build();
                SignedJWT signedJWT = new SignedJWT(header, claims);
                signedJWT.sign(new MACSigner(secretKey));
                return signedJWT.serialize();
            } else {
                JWSHeader header = new JWSHeader.Builder(JWSAlgorithm.RS256)
                    .keyID(keyProvider.getKeyId())
                    .type(JOSEObjectType.JWT)
                    .build();
                SignedJWT signedJWT = new SignedJWT(header, claims);
                signedJWT.sign(new RSASSASigner(keyProvider.getPrivateKey()));
                return signedJWT.serialize();
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate JWT token", e);
        }
    }

    public boolean isTokenValid(String token) {
        try {
            parseClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    public Optional<String> extractSubject(String token) {
        try {
            return Optional.ofNullable(parseClaims(token).getPayload().getSubject());
        } catch (JwtException | IllegalArgumentException e) {
            return Optional.empty();
        }
    }

    private Jws<Claims> parseClaims(String token) {
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token);
    }
}
