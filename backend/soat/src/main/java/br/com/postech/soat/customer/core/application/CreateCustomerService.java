package br.com.postech.soat.customer.core.application;

import br.com.postech.soat.customer.core.domain.exeption.CustomerAlreadyExistsException;
import br.com.postech.soat.customer.core.domain.valueobject.CPF;
import br.com.postech.soat.customer.core.domain.valueobject.CustomerId;
import br.com.postech.soat.customer.core.domain.valueobject.Email;
import br.com.postech.soat.customer.core.domain.valueobject.Name;
import br.com.postech.soat.customer.core.ports.in.CreateCustomerUseCase;
import br.com.postech.soat.customer.core.ports.out.CustomerRepository;
import br.com.postech.soat.openapi.model.Customer;

public class CreateCustomerService implements CreateCustomerUseCase {

    private final CustomerRepository customerRepository;

    public CreateCustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public Customer create(CPF cpf, Name name, Email email) {
        if (customerRepository.existsByCpf(cpf)) {
            throw new CustomerAlreadyExistsException("Cliente com CPF " + cpf.value() + " já existe");
        }

        Customer customer = new Customer(CustomerId.generate().value(), cpf.value(), name.value(), email.value());

        return customerRepository.save(customer);
    }
}