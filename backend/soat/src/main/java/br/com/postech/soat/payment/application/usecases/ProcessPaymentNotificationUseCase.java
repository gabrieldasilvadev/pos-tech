package br.com.postech.soat.payment.application.usecases;

import br.com.postech.soat.commons.infrastructure.aop.monitorable.Monitorable;
import br.com.postech.soat.payment.application.repositories.PaymentRepository;
import br.com.postech.soat.payment.domain.entity.Payment;
import br.com.postech.soat.payment.domain.valueobject.PaymentId;
import br.com.postech.soat.payment.infrastructure.paymentgateway.CheckoutClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Monitorable
@RequiredArgsConstructor
public class ProcessPaymentNotificationUseCase {
  private final PaymentRepository paymentRepository;
  private final CheckoutClient checkoutClient;

  public void execute(PaymentId paymentId) {

    final String paymentProcessed = checkoutClient.getPaymentDetails(paymentId);
    if (paymentProcessed == null || paymentProcessed.trim().isEmpty()) {
      throw new RuntimeException("Failed to process payment notification");
    }

    final Payment payment = paymentRepository.findById(paymentId);

    payment.finish();
    paymentRepository.save(payment);
  }
}
