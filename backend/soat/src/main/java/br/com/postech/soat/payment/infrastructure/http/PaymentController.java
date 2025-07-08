package br.com.postech.soat.payment.infrastructure.http;

import br.com.postech.soat.commons.application.mediator.Mediator;
import br.com.postech.soat.openapi.api.PaymentApi;
import br.com.postech.soat.openapi.model.GetPaymentsPaymentId200ResponseDto;
import br.com.postech.soat.openapi.model.PostPayments202ResponseDto;
import br.com.postech.soat.openapi.model.PostPaymentsRequestDto;
import br.com.postech.soat.payment.application.usecases.FindPaymentByIdUseCase;
import br.com.postech.soat.payment.application.usecases.InitiatePaymentUseCase;
import br.com.postech.soat.payment.domain.entity.Payment;
import br.com.postech.soat.payment.domain.entity.PaymentId;
import br.com.postech.soat.payment.infrastructure.http.mapper.PaymentCommandMapper;
import br.com.postech.soat.payment.infrastructure.http.mapper.PaymentQueryMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PaymentController implements PaymentApi {
    private final Mediator mediator;
    private final InitiatePaymentUseCase initiatePaymentUseCase;
    private final FindPaymentByIdUseCase findPaymentByIdUseCase;

    @Override
    public ResponseEntity<PostPayments202ResponseDto> postPayments(PostPaymentsRequestDto postPaymentsRequestDto) {
        final PaymentId paymentId = initiatePaymentUseCase.process(PaymentCommandMapper.INSTANCE
            .toCommand(postPaymentsRequestDto));
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(PostPayments202ResponseDto.builder()
            .paymentId(paymentId.getValue())
            .build());
    }

    @Override
    public ResponseEntity<GetPaymentsPaymentId200ResponseDto> getPaymentsPaymentId(String paymentId) {
        final Payment payment = findPaymentByIdUseCase.handle(PaymentId.of(paymentId));
        return ResponseEntity.ok(PaymentQueryMapper.INSTANCE.toResponse(payment));
    }
}
