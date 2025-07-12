package br.com.postech.soat.payment.application.usecases;

import br.com.postech.soat.commons.infrastructure.aop.monitorable.Monitorable;
import br.com.postech.soat.payment.application.repositories.PaymentRepository;
import br.com.postech.soat.payment.domain.entity.Payment;
import br.com.postech.soat.payment.domain.valueobject.PaymentId;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Monitorable
public class FindPaymentByIdUseCase {
    private final PaymentRepository paymentRepository;
    private final Logger logger = LoggerFactory.getLogger(FindPaymentByIdUseCase.class);

    public Payment execute(PaymentId paymentId) {
        logger.info("Executing payment query for payment ID: {}", paymentId.getValue());
        return paymentRepository.findById(paymentId);
    }
}
