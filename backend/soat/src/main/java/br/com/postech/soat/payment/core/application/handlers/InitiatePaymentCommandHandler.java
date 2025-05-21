package br.com.postech.soat.payment.core.application.handlers;

import br.com.postech.soat.commons.application.command.CommandHandler;
import br.com.postech.soat.commons.infrastructure.aop.monitorable.Monitorable;
import br.com.postech.soat.payment.core.application.commands.InitiatePaymentCommand;
import br.com.postech.soat.payment.core.domain.model.Payment;
import br.com.postech.soat.payment.core.domain.model.PaymentId;
import br.com.postech.soat.payment.core.ports.out.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Monitorable
public class InitiatePaymentCommandHandler implements CommandHandler<InitiatePaymentCommand, PaymentId> {
    private final PaymentRepository paymentRepository;
    private final Logger logger = LoggerFactory.getLogger(InitiatePaymentCommandHandler.class);

    @Override
    public PaymentId handle(InitiatePaymentCommand command) {
        final Payment payment = Payment.initiate(
            command.orderId(),
            command.customerId(),
            command.paymentMethod(),
            command.amount()
        );

        return payment.getId();
    }
}
