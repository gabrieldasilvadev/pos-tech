package br.com.postech.soat.payment.application.usecases;

import br.com.postech.soat.commons.infrastructure.aop.monitorable.Monitorable;
import br.com.postech.soat.payment.application.repositories.PaymentRepository;
import br.com.postech.soat.payment.domain.entity.Payment;
import br.com.postech.soat.payment.domain.entity.PaymentStatus;
import br.com.postech.soat.payment.domain.valueobject.PaymentId;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Monitorable
public class GetPaymentStatusUseCase {
    private final PaymentRepository paymentRepository;
    private final Logger logger = LoggerFactory.getLogger(GetPaymentStatusUseCase.class);
    
    public PaymentStatus execute(PaymentId paymentId) {
        logger.info("Executing payment status query for payment ID: {}", paymentId.getValue());
        Payment payment = paymentRepository.findById(paymentId);
        return payment.getStatus();
    }
}
