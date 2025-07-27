package br.com.postech.soat.payment.infrastructure.paymentgateway;

import com.mercadopago.MercadoPagoConfig;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MercadoPagoConfiguration {

    @Value("${app.payment-gateway.mercadopago.access-token}")
    private String accessToken;

    @PostConstruct
    public void init() {
        MercadoPagoConfig.setAccessToken(accessToken);
    }
}