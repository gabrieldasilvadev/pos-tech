package br.com.postech.soat.customer.core.application;

import br.com.postech.soat.customer.core.domain.exception.CustomerAlreadyExistsException;
import br.com.postech.soat.customer.core.domain.model.Customer;
import br.com.postech.soat.customer.core.domain.valueobject.CPF;
import br.com.postech.soat.customer.core.domain.valueobject.CustomerId;
import br.com.postech.soat.customer.core.domain.valueobject.Email;
import br.com.postech.soat.customer.core.domain.valueobject.Name;
import br.com.postech.soat.customer.core.domain.valueobject.Phone;
import br.com.postech.soat.customer.core.ports.in.CreateCustomerUseCase;
import br.com.postech.soat.customer.core.ports.out.CustomerRepository;

public class CreateCustomerService implements CreateCustomerUseCase {

    private final CustomerRepository customerRepository;

    public CreateCustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public Customer create(CPF cpf, Name name, Email email, Phone phone) {
        if (customerRepository.existsByCpf(cpf)) {
            throw new CustomerAlreadyExistsException("Cliente com CPF " + cpf.value() + " já existe");
        }

        Customer customer = new Customer();
        customer.setId(CustomerId.generate().value());
        customer.setCpf(cpf.value());
        customer.setName(name.value());
        customer.setEmail(email.value());
        customer.setPhone(phone.value());

        return customerRepository.save(customer);
    }
}