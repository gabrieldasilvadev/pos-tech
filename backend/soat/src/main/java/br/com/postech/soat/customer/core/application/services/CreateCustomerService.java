package br.com.postech.soat.customer.core.application.services;

import br.com.postech.soat.commons.infrastructure.aop.monitorable.Monitorable;
import br.com.postech.soat.commons.infrastructure.exception.ResourceConflictException;
import br.com.postech.soat.customer.core.application.dto.CreateCustomerRequest;
import br.com.postech.soat.customer.core.domain.model.Customer;
import br.com.postech.soat.customer.core.domain.valueobject.CPF;
import br.com.postech.soat.customer.core.domain.valueobject.Email;
import br.com.postech.soat.customer.core.domain.valueobject.Name;
import br.com.postech.soat.customer.core.domain.valueobject.Phone;
import br.com.postech.soat.customer.core.ports.in.CreateCustomerUseCase;
import br.com.postech.soat.customer.core.ports.out.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Monitorable
public class CreateCustomerService implements CreateCustomerUseCase {

    private final Logger logger = LoggerFactory.getLogger(CreateCustomerService.class);
    private final CustomerRepository customerRepository;

    @Transactional
    @Override
    public Customer execute(CreateCustomerRequest request) {
        try {
            CPF cpf = new CPF(request.cpf());
            Name name = new Name(request.name());
            Email email = new Email(request.email());
            Phone phone = new Phone(request.phone());

            customerRepository.findByCpf(cpf.value())
                .ifPresent(c -> {
                    throw new ResourceConflictException(
                        "Customer with document identifier: " + cpf.value() + ", already exists");
                });

            final Customer customer = Customer.create(name, email, cpf, phone);
            logger.info("Domain customer created: {}", customer);

            return customerRepository.save(customer);
        } catch (Exception e) {
            logger.error("Error creating customer: {}", e.getMessage(), e);
            throw e;
        }
    }
}