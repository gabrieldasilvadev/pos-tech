package br.com.postech.soat.payment.application.query;

import br.com.postech.soat.commons.application.query.QueryHandler;
import br.com.postech.soat.commons.infrastructure.aop.monitorable.Monitorable;
import br.com.postech.soat.payment.application.query.model.PaymentQuery;
import br.com.postech.soat.payment.domain.entity.Payment;
import br.com.postech.soat.payment.application.repositories.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Monitorable
public class PaymentQueryHandler implements QueryHandler<PaymentQuery, Payment> {
    private final PaymentRepository paymentRepository;
    private final Logger logger = LoggerFactory.getLogger(PaymentQueryHandler.class);

    @Override
    public Payment handle(PaymentQuery query) {
        logger.info("Executing payment query for payment ID: {}", query.paymentId());
        return paymentRepository.findById(query.paymentId());
    }
}
