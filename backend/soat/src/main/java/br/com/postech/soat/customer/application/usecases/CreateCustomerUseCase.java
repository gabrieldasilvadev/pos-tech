package br.com.postech.soat.customer.application.usecases;

import br.com.postech.soat.commons.infrastructure.exception.ResourceConflictException;
import br.com.postech.soat.customer.application.dto.CreateCustomerDto;
import br.com.postech.soat.customer.domain.model.Customer;
import br.com.postech.soat.customer.domain.valueobject.CPF;
import br.com.postech.soat.customer.domain.valueobject.Email;
import br.com.postech.soat.customer.domain.valueobject.Name;
import br.com.postech.soat.customer.domain.valueobject.Phone;
import br.com.postech.soat.customer.application.repositories.CustomerRepository;

public class CreateCustomerUseCase {

    private final CustomerRepository customerRepository;

    public CreateCustomerUseCase(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer execute(CreateCustomerDto command) {
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