package br.com.postech.soat.payment.infrastructure.http;

import br.com.postech.soat.openapi.model.GetPaymentsPaymentIdStatus200ResponseDto;
import br.com.postech.soat.openapi.model.PaymentStatusDto;
import br.com.postech.soat.payment.application.usecases.FindPaymentByIdUseCase;
import br.com.postech.soat.payment.application.usecases.GetPaymentStatusUseCase;
import br.com.postech.soat.payment.application.usecases.InitiatePaymentUseCase;
import br.com.postech.soat.payment.domain.entity.PaymentStatus;
import br.com.postech.soat.payment.domain.valueobject.PaymentId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Payment Controller Tests")
class PaymentControllerTest {

    @Mock
    private InitiatePaymentUseCase initiatePaymentUseCase;

    @Mock
    private FindPaymentByIdUseCase findPaymentByIdUseCase;

    @Mock
    private GetPaymentStatusUseCase getPaymentStatusUseCase;

    private PaymentController paymentController;

    @BeforeEach
    void setUp() {
        paymentController = new PaymentController(
            initiatePaymentUseCase,
            findPaymentByIdUseCase,
            getPaymentStatusUseCase
        );
    }

    @Nested
    @DisplayName("Tests for the payment status consultation API")
    class GetPaymentStatus {

        private final String VALID_PAYMENT_ID = "123e4567-e89b-12d3-a456-426614174000";
        private final UUID VALID_UUID = UUID.fromString(VALID_PAYMENT_ID);

        @Test
        @DisplayName("Should return PENDING status when payment is pending")
        void givenPendingPayment_whenGetPaymentStatus_thenReturnPendingStatus() {
            // Arrange
            PaymentId paymentId = PaymentId.of(VALID_PAYMENT_ID);
            when(getPaymentStatusUseCase.execute(paymentId)).thenReturn(PaymentStatus.PENDING);

            // Act
            ResponseEntity<GetPaymentsPaymentIdStatus200ResponseDto> response = 
                paymentController.getPaymentsPaymentIdStatus(VALID_PAYMENT_ID);

            // Assert
            assertNotNull(response);
            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertNotNull(response.getBody());
            assertEquals(VALID_UUID, response.getBody().getPaymentId());
            assertEquals(PaymentStatusDto.PENDING, response.getBody().getStatus());
            verify(getPaymentStatusUseCase).execute(paymentId);
        }

        @Test
        @DisplayName("Should return APPROVED status when payment is approved")
        void givenApprovedPayment_whenGetPaymentStatus_thenReturnApprovedStatus() {
            // Arrange
            PaymentId paymentId = PaymentId.of(VALID_PAYMENT_ID);
            when(getPaymentStatusUseCase.execute(paymentId)).thenReturn(PaymentStatus.APPROVED);

            // Act
            ResponseEntity<GetPaymentsPaymentIdStatus200ResponseDto> response = 
                paymentController.getPaymentsPaymentIdStatus(VALID_PAYMENT_ID);

            // Assert
            assertNotNull(response);
            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertNotNull(response.getBody());
            assertEquals(VALID_UUID, response.getBody().getPaymentId());
            assertEquals(PaymentStatusDto.APPROVED, response.getBody().getStatus());
            verify(getPaymentStatusUseCase).execute(paymentId);
        }

        @Test
        @DisplayName("Should return FINISHED status when payment is finished")
        void givenFinishedPayment_whenGetPaymentStatus_thenReturnFinishedStatus() {
            // Arrange
            PaymentId paymentId = PaymentId.of(VALID_PAYMENT_ID);
            when(getPaymentStatusUseCase.execute(paymentId)).thenReturn(PaymentStatus.FINISHED);

            // Act
            ResponseEntity<GetPaymentsPaymentIdStatus200ResponseDto> response = 
                paymentController.getPaymentsPaymentIdStatus(VALID_PAYMENT_ID);

            // Assert
            assertNotNull(response);
            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertNotNull(response.getBody());
            assertEquals(VALID_UUID, response.getBody().getPaymentId());
            assertEquals(PaymentStatusDto.FINISHED, response.getBody().getStatus());
            verify(getPaymentStatusUseCase).execute(paymentId);
        }

        @Test
        @DisplayName("Should return FAILED status when payment failed")
        void givenFailedPayment_whenGetPaymentStatus_thenReturnFailedStatus() {
            // Arrange
            PaymentId paymentId = PaymentId.of(VALID_PAYMENT_ID);
            when(getPaymentStatusUseCase.execute(paymentId)).thenReturn(PaymentStatus.FAILED);

            // Act
            ResponseEntity<GetPaymentsPaymentIdStatus200ResponseDto> response = 
                paymentController.getPaymentsPaymentIdStatus(VALID_PAYMENT_ID);

            // Assert
            assertNotNull(response);
            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertNotNull(response.getBody());
            assertEquals(VALID_UUID, response.getBody().getPaymentId());
            assertEquals(PaymentStatusDto.FAILED, response.getBody().getStatus());
            verify(getPaymentStatusUseCase).execute(paymentId);
        }

        @Test
        @DisplayName("Should return DECLINED status when payment is declined")
        void givenDeclinedPayment_whenGetPaymentStatus_thenReturnDeclinedStatus() {
            // Arrange
            PaymentId paymentId = PaymentId.of(VALID_PAYMENT_ID);
            when(getPaymentStatusUseCase.execute(paymentId)).thenReturn(PaymentStatus.DECLINED);

            // Act
            ResponseEntity<GetPaymentsPaymentIdStatus200ResponseDto> response = 
                paymentController.getPaymentsPaymentIdStatus(VALID_PAYMENT_ID);

            // Assert
            assertNotNull(response);
            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertNotNull(response.getBody());
            assertEquals(VALID_UUID, response.getBody().getPaymentId());
            assertEquals(PaymentStatusDto.DECLINED, response.getBody().getStatus());
            verify(getPaymentStatusUseCase).execute(paymentId);
        }

        @Test
        @DisplayName("Should properly convert PaymentId when calling use case")
        void givenPaymentIdString_whenGetPaymentStatus_thenConvertToPaymentIdValueObject() {
            // Arrange
            PaymentId expectedPaymentId = PaymentId.of(VALID_PAYMENT_ID);
            when(getPaymentStatusUseCase.execute(expectedPaymentId)).thenReturn(PaymentStatus.APPROVED);

            // Act
            paymentController.getPaymentsPaymentIdStatus(VALID_PAYMENT_ID);

            // Assert
            verify(getPaymentStatusUseCase).execute(expectedPaymentId);
        }
    }
}
