package br.com.postech.soat.commons.infrastructure.security;

import br.com.postech.soat.commons.infrastructure.security.local.JwtAuthenticationFilterLocal;
import br.com.postech.soat.commons.infrastructure.security.local.JwtTokenServiceLocal;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import java.io.IOException;
import java.time.Clock;
import java.time.Duration;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class JwtAuthenticationFilterLocalTest {

    private JwtTokenServiceLocal jwtTokenService;
    private JwtAuthenticationFilterLocal filter;

    @BeforeEach
    void setUp() {
        SecurityProperties properties = new SecurityProperties();
        properties.getJwt().setSecret("0123456789-0123456789-0123456789-0123456789");
        properties.getJwt().setExpiration(Duration.ofMinutes(5));

        Clock clock = Clock.systemUTC();
        jwtTokenService = new JwtTokenServiceLocal(properties, clock);
        filter = new JwtAuthenticationFilterLocal(jwtTokenService);
    }

    @AfterEach
    void tearDown() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void shouldAuthenticateRequestWithValidBearerToken() throws ServletException, IOException {
        String cpf = "12345678901";
        String token = jwtTokenService.generateToken(cpf, List.of("USER"));

        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + token);
        MockHttpServletResponse response = new MockHttpServletResponse();

        FilterChain chain = new NoopFilterChain();

        filter.doFilter(request, response, chain);

        assertNotNull(SecurityContextHolder.getContext().getAuthentication());
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        assertEquals(cpf, principal.getUsername());
        assertTrue(principal.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_USER")));
    }

    @Test
    void shouldNotAuthenticateWhenNoTokenPresent() throws ServletException, IOException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        FilterChain chain = new NoopFilterChain();

        filter.doFilter(request, response, chain);

        assertNull(SecurityContextHolder.getContext().getAuthentication());
    }

    private static class NoopFilterChain implements FilterChain {
        @Override
        public void doFilter(ServletRequest request, ServletResponse response) {
        }
    }
}
