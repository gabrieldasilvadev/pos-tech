package br.com.postech.soat.payment.adapters.in.http;

import br.com.postech.soat.commons.application.mediator.Mediator;
import br.com.postech.soat.openapi.api.PaymentApi;
import br.com.postech.soat.openapi.model.GetPaymentsPaymentId200ResponseDto;
import br.com.postech.soat.openapi.model.PostPayments202ResponseDto;
import br.com.postech.soat.openapi.model.PostPaymentsRequestDto;
import br.com.postech.soat.payment.adapters.in.http.mapper.PaymentCommandMapper;
import br.com.postech.soat.payment.adapters.in.http.mapper.PaymentQueryMapper;
import br.com.postech.soat.payment.core.application.services.query.model.PaymentQuery;
import br.com.postech.soat.payment.core.domain.model.Payment;
import br.com.postech.soat.payment.core.domain.model.PaymentId;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PaymentController implements PaymentApi {
    private final Mediator mediator;

    @Override
    public ResponseEntity<PostPayments202ResponseDto> postPayments(PostPaymentsRequestDto postPaymentsRequestDto) {
        final PaymentId paymentId = mediator.send(PaymentCommandMapper.INSTANCE.toCommand(postPaymentsRequestDto));
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(PostPayments202ResponseDto.builder()
            .paymentId(paymentId.getValue())
            .build());
    }

    @Override
    public ResponseEntity<GetPaymentsPaymentId200ResponseDto> getPaymentsPaymentId(String paymentId) {
        final Payment payment = mediator.send(new PaymentQuery(PaymentId.of(paymentId)));
        return ResponseEntity.ok(PaymentQueryMapper.INSTANCE.toResponse(payment));
    }
}
