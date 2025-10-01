package br.com.postech.soat.commons.infrastructure.security;

import static org.junit.jupiter.api.Assertions.*;

import java.time.Clock;
import java.time.Duration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class JwtTokenServiceTest {

    private JwtTokenService jwtTokenService;

    @BeforeEach
    void setUp() {
        SecurityProperties properties = new SecurityProperties();
        properties.getJwt().setSecret("0123456789-0123456789-0123456789-0123456789");
        properties.getJwt().setExpiration(Duration.ofMinutes(5));
        Clock clock = Clock.systemUTC();

        jwtTokenService = new JwtTokenService(properties, clock);
        jwtTokenService.init();
    }

    @Test
    void shouldGenerateValidateAndExtractSubject() {
        String subject = "12345678901";
        String token = jwtTokenService.generateToken(subject);

        assertNotNull(token);
        assertTrue(jwtTokenService.isTokenValid(token));
        assertEquals(subject, jwtTokenService.extractSubject(token).orElseThrow());
    }

    @Test
    void shouldReturnInvalidForCorruptedToken() {
        String token = "invalid.token.value";
        assertFalse(jwtTokenService.isTokenValid(token));
        assertTrue(jwtTokenService.extractSubject(token).isEmpty());
    }
}
