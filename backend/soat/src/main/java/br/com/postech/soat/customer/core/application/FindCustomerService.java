package br.com.postech.soat.customer.core.application;

import br.com.postech.soat.customer.core.domain.valueobject.CPF;
import br.com.postech.soat.customer.core.ports.in.FindCustomerUseCase;
import br.com.postech.soat.customer.core.ports.out.CustomerRepository;
import br.com.postech.soat.openapi.model.Customer;
import java.util.List;

public class FindCustomerService implements FindCustomerUseCase {

    private final CustomerRepository customerRepository;

    public FindCustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public List<Customer> find(CPF cpf) {
        return customerRepository.find(cpf);
    }
}
