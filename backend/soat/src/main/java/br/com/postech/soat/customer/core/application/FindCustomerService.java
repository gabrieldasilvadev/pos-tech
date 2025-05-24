package br.com.postech.soat.customer.core.application;

import br.com.postech.soat.commons.infrastructure.exception.NotFoundException;
import br.com.postech.soat.customer.core.application.dto.FindCustomerQuery;
import br.com.postech.soat.customer.core.domain.model.Customer;
import br.com.postech.soat.customer.core.ports.in.FindCustomerUseCase;
import br.com.postech.soat.customer.core.ports.out.CustomerRepository;
import org.springframework.transaction.annotation.Transactional;

public class FindCustomerService implements FindCustomerUseCase {

    private final CustomerRepository customerRepository;

    public FindCustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Transactional(readOnly = true)
    @Override
    public Customer findByCpf(FindCustomerQuery query) {
        return customerRepository.findByCpf(query.cpf())
            .orElseThrow(() -> new NotFoundException("Customer not found for the document identifier: " + query.cpf()));
    }
}
