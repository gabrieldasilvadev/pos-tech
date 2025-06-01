package br.com.postech.soat.customer.core.application;

import br.com.postech.soat.commons.infrastructure.aop.monitorable.Monitorable;
import br.com.postech.soat.commons.infrastructure.exception.ResourceConflictException;
import br.com.postech.soat.customer.core.application.dto.CreateCustomerCommand;
import br.com.postech.soat.customer.core.domain.model.Customer;
import br.com.postech.soat.customer.core.domain.valueobject.CPF;
import br.com.postech.soat.customer.core.domain.valueobject.Email;
import br.com.postech.soat.customer.core.domain.valueobject.Name;
import br.com.postech.soat.customer.core.domain.valueobject.Phone;
import br.com.postech.soat.customer.core.ports.in.CreateCustomerUseCase;
import br.com.postech.soat.customer.core.ports.out.CustomerRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Monitorable
public class CreateCustomerService implements CreateCustomerUseCase {

    private final Logger logger = LoggerFactory.getLogger(CreateCustomerService.class);

    private final CustomerRepository customerRepository;

    @Transactional
    @Override
    public Customer create(CreateCustomerCommand command) {
        try {
            CPF cpf = new CPF(command.cpf());
            Name name = new Name(command.name());
            Email email = new Email(command.email());
            Phone phone = new Phone(command.phone());

            List<String> conflicts = validateUniqueFields(cpf, email, phone);

            if (!conflicts.isEmpty()) {
                throw new ResourceConflictException(
                    "Customer registration failed. Conflicts found: " + String.join(", ", conflicts));
            }

            final Customer customer = Customer.create(name, email, cpf, phone);
            logger.info("Domain customer created: {}", customer);

            return customerRepository.save(customer);
        } catch (Exception e) {
            logger.error("Error creating customer: {}", e.getMessage(), e);
            throw e;
        }
    }

    private List<String> validateUniqueFields(CPF cpf, Email email, Phone phone) {
        List<String> conflicts = new ArrayList<>();

        if (customerRepository.existsByCpf(cpf.value())) {
            conflicts.add("CPF: " + cpf.value());
        }

        if (customerRepository.existsByEmail(email.value())) {
            conflicts.add("Email: " + email.value());
        }

        if (phone != null && phone.value() != null &&
            customerRepository.existsByPhone(phone.value())) {
            conflicts.add("Phone: " + phone.value());
        }

        return conflicts;
    }
}