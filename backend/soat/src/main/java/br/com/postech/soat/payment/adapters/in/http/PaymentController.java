package br.com.postech.soat.payment.adapters.in.http;

import br.com.postech.soat.openapi.api.PaymentApi;
import br.com.postech.soat.openapi.model.PostPayments202ResponseDto;
import br.com.postech.soat.openapi.model.PostPaymentsRequestDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PaymentController implements PaymentApi {
    @Override
    public ResponseEntity<PostPayments202ResponseDto> postPayments(PostPaymentsRequestDto postPaymentsRequestDto) {
        return PaymentApi.super.postPayments(postPaymentsRequestDto);
    }
}
