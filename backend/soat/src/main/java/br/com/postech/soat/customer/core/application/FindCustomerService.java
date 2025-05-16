package br.com.postech.soat.customer.core.application;

import br.com.postech.soat.customer.core.domain.model.Customer;
import br.com.postech.soat.customer.core.domain.valueobject.CPF;
import br.com.postech.soat.customer.core.ports.in.FindCustomerUseCase;
import br.com.postech.soat.customer.core.ports.out.CustomerRepository;

public class FindCustomerService implements FindCustomerUseCase {

    private final CustomerRepository customerRepository;

    public FindCustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public Customer findByCpf(CPF cpf) {
        return customerRepository.findByCpf(cpf);
    }
}
