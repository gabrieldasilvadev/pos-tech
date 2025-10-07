package br.com.postech.soat.commons.infrastructure.security;

import br.com.postech.soat.commons.infrastructure.security.http.AuthenticationRequest;
import br.com.postech.soat.payment.domain.entity.Payment;
import br.com.postech.soat.payment.domain.valueobject.PaymentId;
import br.com.postech.soat.payment.infrastructure.http.PostgresTestContainerConfig;
import br.com.postech.soat.customer.application.repositories.CustomerRepository;
import br.com.postech.soat.customer.domain.entity.Customer;
import br.com.postech.soat.customer.domain.valueobject.CPF;
import br.com.postech.soat.customer.domain.valueobject.Email;
import br.com.postech.soat.customer.domain.valueobject.Name;
import br.com.postech.soat.customer.domain.valueobject.Phone;
import br.com.postech.soat.payment.infrastructure.paymentgateway.CheckoutClient;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = {br.com.postech.soat.SoatApplication.class, JwtSecurityIntegrationTest.TestBeans.class})
@AutoConfigureMockMvc
@ActiveProfiles("test")
@DisplayName("JWT Security Integration Tests")
class JwtSecurityIntegrationTest extends PostgresTestContainerConfig {

    @TestConfiguration
    static class TestBeans {
        @Bean
        public CheckoutClient checkoutClient() {
            return new CheckoutClient() {
                @Override
                public String createPayment(Payment payment) {
                    return "http://test/payment/" + payment.getId().getValue();
                }
                @Override
                public String getPaymentDetails(PaymentId paymentId) {
                    return "processed";
                }
            };
        }
    }

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private final String existingCpf = "12345678901";

    @BeforeEach
    void setUp() {
        customerRepository.findByCpf(existingCpf).orElseGet(() ->
            customerRepository.save(
                Customer.create(
                    new Name("John Doe"),
                    new Email("john.doe@example.com"),
                    new CPF(existingCpf),
                    new Phone("11987654321")
                )
            )
        );
    }

    @Test
    @DisplayName("POST /customers deve ser público (sem JWT)")
    void createCustomerShouldBePublic() throws Exception {
        String body = "{" +
            "\"name\":\"Jane Roe\"," +
            "\"email\":\"jane.roe@example.com\"," +
            "\"cpf\":\"98765432100\"," +
            "\"phone\":\"11999999999\"}";

        mockMvc.perform(post("/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
            .andExpect(status().isCreated())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @DisplayName("GET /customers deve exigir JWT - sem token retorna 401")
    void findCustomerShouldRequireJwt() throws Exception {
        mockMvc.perform(get("/customers").param("cpf", existingCpf))
            .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("Login retorna token e permite acesso ao endpoint protegido")
    void loginThenAccessProtectedEndpoint() throws Exception {
        AuthenticationRequest request = new AuthenticationRequest(existingCpf);

        MvcResult loginResult = mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andReturn();

        String token = extractToken(loginResult);
        assertThat(token).isNotBlank();

        mockMvc.perform(get("/customers")
                .param("cpf", existingCpf)
                .header("Authorization", "Bearer " + token))
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.cpf").value(existingCpf));
    }

    @Test
    @DisplayName("Refresh token deve emitir novo token válido")
    void refreshTokenFlow() throws Exception {
        AuthenticationRequest request = new AuthenticationRequest(existingCpf);
        String token = extractToken(mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isOk())
            .andReturn());

        MvcResult refreshResult = mockMvc.perform(post("/auth/refresh")
                .header("Authorization", "Bearer " + token))
            .andExpect(status().isOk())
            .andReturn();

        String refreshed = extractToken(refreshResult);
        assertThat(refreshed).isNotBlank();

        mockMvc.perform(get("/customers")
                .param("cpf", existingCpf)
                .header("Authorization", "Bearer " + refreshed))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.cpf").value(existingCpf));
    }

    private String extractToken(MvcResult result) throws Exception {
        String json = result.getResponse().getContentAsString();
        JsonNode node = objectMapper.readTree(json);
        if (node.has("token")) {
            return node.get("token").asText();
        }
        
        return json.replace("\"", "").replace("{token:", "").replace("}", "").trim();
    }
}
