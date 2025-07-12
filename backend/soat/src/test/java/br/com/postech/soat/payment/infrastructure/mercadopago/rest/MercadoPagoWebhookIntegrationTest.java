package br.com.postech.soat.payment.infrastructure.mercadopago.rest;

import br.com.postech.soat.customer.domain.valueobject.CustomerId;
import br.com.postech.soat.order.core.domain.model.OrderId;
import br.com.postech.soat.payment.core.domain.model.Payment;
import br.com.postech.soat.payment.core.domain.model.PaymentId;
import br.com.postech.soat.payment.core.domain.model.PaymentMethod;
import br.com.postech.soat.payment.core.domain.model.PaymentStatus;
import br.com.postech.soat.payment.core.ports.out.PaymentRepository;
import java.math.BigDecimal;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test")
@DisplayName("MercadoPago Webhook Integration Tests")
class MercadoPagoWebhookIntegrationTest extends PostgresTestContainerConfig {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PaymentRepository paymentRepository;

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
