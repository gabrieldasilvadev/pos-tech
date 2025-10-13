package br.com.postech.soat.payment.infrastructure.http;

import br.com.postech.soat.payment.application.usecases.ProcessPaymentNotificationUseCase;
import br.com.postech.soat.payment.domain.valueobject.PaymentId;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/webhooks/mercado-pago")
@RequiredArgsConstructor
public class MercadoPagoWebhookController {
    private final ProcessPaymentNotificationUseCase notificationService;

    @PostMapping(consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<Void> receive(@RequestParam String id, @RequestParam String topic) {
        if (topic.equals("payment")) {
            notificationService.execute(PaymentId.of(id));
        }
        return ResponseEntity.ok().build();
    }
}
