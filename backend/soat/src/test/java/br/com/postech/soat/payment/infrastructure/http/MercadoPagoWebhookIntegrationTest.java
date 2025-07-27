package br.com.postech.soat.payment.infrastructure.http;

import br.com.postech.soat.customer.domain.valueobject.CustomerId;
import br.com.postech.soat.order.domain.valueobject.OrderId;
import br.com.postech.soat.payment.application.repositories.PaymentRepository;
import br.com.postech.soat.payment.domain.entity.Payment;
import br.com.postech.soat.payment.domain.entity.PaymentMethod;
import br.com.postech.soat.payment.domain.entity.PaymentStatus;
import br.com.postech.soat.payment.domain.valueobject.PaymentId;
import br.com.postech.soat.payment.infrastructure.paymentgateway.FakeCheckoutClient;
import br.com.postech.soat.product.application.adapters.LoggerPort;
import br.com.postech.soat.product.infrastructure.LoggerAdapter;
import java.math.BigDecimal;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles({"test", "fakeCheckoutClient"})
@DisplayName("MercadoPago Webhook Integration Tests")
class MercadoPagoWebhookIntegrationTest extends PostgresTestContainerConfig {

    @TestConfiguration
    static class ExcludeMockControllersConfig {

        @Bean
        public static BeanFactoryPostProcessor excludeProductController() {
            return beanFactory -> {
                if (beanFactory instanceof BeanDefinitionRegistry registry) {
                    if (registry.containsBeanDefinition("productController")) {
                        registry.removeBeanDefinition("productController");
                    }
                    if (registry.containsBeanDefinition("customerController")) {
                        registry.removeBeanDefinition("customerController");
                    }
                }
            };
        }

        @Bean
        public LoggerPort loggerPort() {
            return new LoggerAdapter();
        }
    }

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private FakeCheckoutClient fakeCheckoutClient;

    private Payment testPayment;
    private PaymentId paymentId;

    @BeforeEach
    void setUp() {
        OrderId orderId = new OrderId(UUID.randomUUID());
        CustomerId customerId = new CustomerId(UUID.randomUUID());

        testPayment = Payment.initiate(orderId, customerId, PaymentMethod.PIX, new BigDecimal("100.00"));

        testPayment.approve();

        paymentId = testPayment.getId();

        paymentRepository.save(testPayment);
    }

    @Test
    @DisplayName("Should process webhook and update payment status")
    void shouldProcessWebhookAndUpdatePaymentStatus() throws Exception {
        fakeCheckoutClient.createPayment(testPayment);
        Payment initialPayment = paymentRepository.findById(paymentId);
        assertNotNull(initialPayment);
        assertEquals(PaymentStatus.APPROVED, initialPayment.getStatus());

        mockMvc.perform(MockMvcRequestBuilders.post("/webhooks/mercado-pago")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", paymentId.getValue().toString())
                .param("topic", "payment"))
            .andExpect(MockMvcResultMatchers.status().isOk());

        Payment updatedPayment = paymentRepository.findById(paymentId);
        assertNotNull(updatedPayment);
        assertEquals(PaymentStatus.FINISHED, updatedPayment.getStatus());
    }

    @Test
    @DisplayName("Should not update payment status when topic is not payment")
    void shouldNotUpdatePaymentStatusWhenTopicIsNotPayment() throws Exception {
        Payment initialPayment = paymentRepository.findById(paymentId);
        assertNotNull(initialPayment);
        assertEquals(PaymentStatus.APPROVED, initialPayment.getStatus());

        mockMvc.perform(MockMvcRequestBuilders.post("/webhooks/mercado-pago")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", paymentId.getValue().toString())
                .param("topic", "other_topic"))
            .andExpect(MockMvcResultMatchers.status().isOk());

        Payment updatedPayment = paymentRepository.findById(paymentId);
        assertNotNull(updatedPayment);
        assertEquals(PaymentStatus.APPROVED, updatedPayment.getStatus());
    }
}
