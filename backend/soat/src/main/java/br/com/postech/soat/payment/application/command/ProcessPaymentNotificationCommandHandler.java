package br.com.postech.soat.payment.application.command;

import br.com.postech.soat.commons.infrastructure.aop.monitorable.Monitorable;
import br.com.postech.soat.payment.application.repositories.PaymentRepository;
import br.com.postech.soat.payment.domain.entity.Payment;
import br.com.postech.soat.payment.domain.entity.PaymentId;
import br.com.postech.soat.payment.infrastructure.paymentgateway.FakeCheckoutClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Monitorable
@RequiredArgsConstructor
public class ProcessPaymentNotificationCommandHandler {
  private final PaymentRepository paymentRepository;
  private final FakeCheckoutClient fakeCheckoutClient;

  public void handle(PaymentId paymentId) {

    final String paymentProcessed = fakeCheckoutClient.getPaymentDetails(paymentId);
    if (paymentProcessed == null || paymentProcessed.trim().isEmpty()) {
      throw new RuntimeException("Failed to process payment notification");
    }

    final Payment payment = paymentRepository.findById(paymentId);

    payment.finish();
    paymentRepository.save(payment);
  }
}
