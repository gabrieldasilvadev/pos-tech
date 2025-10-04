package br.com.postech.soat.commons.infrastructure.security;

import static org.junit.jupiter.api.Assertions.*;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import java.io.IOException;
import java.time.Clock;
import java.time.Duration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpHeaders;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

class JwtAuthenticationFilterTest {

    private JwtTokenService jwtTokenService;
    private JwtAuthenticationFilter filter;

    @BeforeEach
    void setUp() {
        SecurityProperties properties = new SecurityProperties();
        properties.getJwt().setSecret("0123456789-0123456789-0123456789-0123456789");
        properties.getJwt().setExpiration(Duration.ofMinutes(5));
        Clock clock = Clock.systemUTC();
        jwtTokenService = new JwtTokenService(properties, clock);
        jwtTokenService.init();

        CustomerUserDetailsService userDetailsService = Mockito.mock(CustomerUserDetailsService.class);
        Mockito.when(userDetailsService.loadUserByUsername(Mockito.anyString()))
            .thenAnswer(invocation -> {
                String username = invocation.getArgument(0);
                return User.withUsername(username).password("").authorities("ROLE_USER").build();
            });
        filter = new JwtAuthenticationFilter(jwtTokenService, userDetailsService);
    }

    @AfterEach
    void tearDown() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void shouldAuthenticateRequestWithValidBearerToken() throws ServletException, IOException {
        String cpf = "12345678901";
        String token = jwtTokenService.generateToken(cpf);

        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + token);
        MockHttpServletResponse response = new MockHttpServletResponse();

        FilterChain chain = new NoopFilterChain();

        filter.doFilter(request, response, chain);

        assertNotNull(SecurityContextHolder.getContext().getAuthentication());
        assertEquals(cpf, ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());
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
