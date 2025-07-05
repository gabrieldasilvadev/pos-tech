package br.com.postech.soat.customer.core.application;

import br.com.postech.soat.commons.infrastructure.exception.ResourceConflictException;
import br.com.postech.soat.customer.core.application.dto.CreateCustomerCommand;
import br.com.postech.soat.customer.core.domain.model.Customer;
import br.com.postech.soat.customer.core.domain.valueobject.CPF;
import br.com.postech.soat.customer.core.domain.valueobject.Email;
import br.com.postech.soat.customer.core.domain.valueobject.Name;
import br.com.postech.soat.customer.core.domain.valueobject.Phone;
import br.com.postech.soat.customer.core.ports.in.CreateCustomerUseCase;
import br.com.postech.soat.customer.core.ports.out.CustomerRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CreateCustomerHandler implements CreateCustomerUseCase {

    private final CustomerRepository customerRepository;

    @Override
    public Customer execute(CreateCustomerCommand command) {
        CPF cpf = new CPF(command.cpf());
        Name name = new Name(command.name());
        Email email = new Email(command.email());
        Phone phone = new Phone(command.phone());

        validateCustomerDoesNotExist(cpf);

        final Customer customer = Customer.create(name, email, cpf, phone);
        return customerRepository.save(customer);
    }

    private void validateCustomerDoesNotExist(CPF cpf) {
        customerRepository.findByCpf(cpf.value())
            .ifPresent(c -> {
                throw new ResourceConflictException(
                    "Customer with document identifier: " + cpf.value() + ", already exists");
            });
    }
}