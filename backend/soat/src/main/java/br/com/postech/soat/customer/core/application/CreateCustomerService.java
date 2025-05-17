package br.com.postech.soat.customer.core.application;

import br.com.postech.soat.customer.core.domain.exception.CustomerAlreadyExistsException;
import br.com.postech.soat.customer.core.domain.model.Customer;
import br.com.postech.soat.customer.core.domain.valueobject.CPF;
import br.com.postech.soat.customer.core.domain.valueobject.Email;
import br.com.postech.soat.customer.core.domain.valueobject.Name;
import br.com.postech.soat.customer.core.domain.valueobject.Phone;
import br.com.postech.soat.customer.core.ports.in.CreateCustomerUseCase;
import br.com.postech.soat.customer.core.ports.out.CustomerRepository;
import org.springframework.transaction.annotation.Transactional;

public class CreateCustomerService implements CreateCustomerUseCase {

    private final CustomerRepository customerRepository;

    public CreateCustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Transactional
    @Override
    public Customer create(CreateCustomerCommand command) {
        CPF cpf = new CPF(command.cpf());
        Name name = new Name(command.name());
        Email email = new Email(command.email());
        Phone phone = new Phone(command.phone());

        customerRepository.findByCpf(cpf.value())
            .ifPresent(c -> {
                throw new CustomerAlreadyExistsException("Cliente com CPF " + cpf.value() + " já existe");
            });

        Customer customer = Customer.create(name, email, cpf, phone);
        return customerRepository.save(customer);
    }
}