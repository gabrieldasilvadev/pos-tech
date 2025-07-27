package br.com.postech.soat.payment.infrastructure.paymentgateway;

import br.com.postech.soat.payment.domain.entity.Payment;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;

public interface CheckoutClient {
    String createPayment(Payment paymentId) throws MPException, MPApiException;
}
