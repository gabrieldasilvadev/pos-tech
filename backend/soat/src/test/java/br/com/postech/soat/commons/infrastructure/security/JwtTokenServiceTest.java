package br.com.postech.soat.commons.infrastructure.security;

import java.time.Clock;
import java.time.Duration;
import java.util.List;

import br.com.postech.soat.commons.infrastructure.security.local.JwtTokenServiceLocal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class JwtTokenServiceTest {

    private JwtTokenServiceLocal jwtTokenService;

    @BeforeEach
    void setUp() {
        SecurityProperties properties = new SecurityProperties();
        properties.getJwt().setSecret("super-secret-super-secret-super-secret-super-secret");
        properties.getJwt().setExpiration(Duration.ofMinutes(5));
        Clock clock = Clock.systemUTC();
        jwtTokenService = new JwtTokenServiceLocal(properties, clock);
    }

    @Test
    void shouldGenerateValidateAndExtractSubject() {
        String subject = "12345678901";
        String token = jwtTokenService.generateToken(subject, List.of("USER"));

        assertNotNull(token);
        assertTrue(jwtTokenService.isTokenValid(token));
        assertEquals(subject, jwtTokenService.extractSubject(token).orElseThrow());
    }


    @Test
    void shouldReturnInvalidForCorruptedToken() {
        String token = "invalid.token.value";
        assertFalse(jwtTokenService.isTokenValid(token), "Token inválido deveria retornar falso");
        assertTrue(jwtTokenService.extractSubject(token).isEmpty(), "Subject não deveria ser extraído de token inválido");
    }
}
