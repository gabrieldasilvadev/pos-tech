package br.com.postech.soat.payment.infrastructure.http;

import br.com.postech.soat.openapi.api.PaymentApi;
import br.com.postech.soat.openapi.model.GetPaymentsPaymentId200ResponseDto;
import br.com.postech.soat.openapi.model.GetPaymentsPaymentIdStatus200ResponseDto;
import br.com.postech.soat.openapi.model.PaymentInitiationResultDto;
import br.com.postech.soat.openapi.model.PostPaymentsRequestDto;
import br.com.postech.soat.payment.application.dto.PaymentInitiationResult;
import br.com.postech.soat.payment.application.usecases.FindPaymentByIdUseCase;
import br.com.postech.soat.payment.application.usecases.GetPaymentStatusUseCase;
import br.com.postech.soat.payment.application.usecases.InitiatePaymentUseCase;
import br.com.postech.soat.payment.domain.entity.Payment;
import br.com.postech.soat.payment.domain.entity.PaymentStatus;
import br.com.postech.soat.payment.domain.valueobject.PaymentId;
import br.com.postech.soat.payment.infrastructure.http.mapper.PaymentCommandMapper;
import br.com.postech.soat.payment.infrastructure.http.mapper.PaymentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import java.net.URI;

@RestController
@RequiredArgsConstructor
public class PaymentController implements PaymentApi {
    private final InitiatePaymentUseCase initiatePaymentUseCase;
    private final FindPaymentByIdUseCase findPaymentByIdUseCase;
    private final GetPaymentStatusUseCase getPaymentStatusUseCase;

    @Override
    public ResponseEntity<PaymentInitiationResultDto> postPayments(PostPaymentsRequestDto postPaymentsRequestDto) {
        final PaymentInitiationResult paymentInitiationResult = initiatePaymentUseCase.execute(PaymentCommandMapper.INSTANCE
            .toCommand(postPaymentsRequestDto));

        PaymentInitiationResultDto responseDto = PaymentInitiationResultDto.builder()
            .paymentId(paymentInitiationResult.paymentId().getValue())
            .paymentUrl(paymentInitiationResult.paymentUrl() != null ?
                URI.create(paymentInitiationResult.paymentUrl()) : null)
            .build();

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(responseDto);
    }

    @Override
    public ResponseEntity<GetPaymentsPaymentId200ResponseDto> getPaymentsPaymentId(String paymentId) {
        final Payment payment = findPaymentByIdUseCase.execute(PaymentId.of(paymentId));
        return ResponseEntity.ok(PaymentMapper.INSTANCE.toResponse(payment));
    }

    @Override
    public ResponseEntity<GetPaymentsPaymentIdStatus200ResponseDto> getPaymentsPaymentIdStatus(String paymentId) {
        final PaymentStatus status = getPaymentStatusUseCase.execute(PaymentId.of(paymentId));
        return ResponseEntity.ok(
            GetPaymentsPaymentIdStatus200ResponseDto.builder()
                .paymentId(PaymentId.of(paymentId).getValue())
                .status(br.com.postech.soat.openapi.model.PaymentStatusDto.fromValue(status.name()))
                .build()
        );
    }
}
