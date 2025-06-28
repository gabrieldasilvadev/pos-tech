package br.com.postech.soat.customer.core.application.services;

import br.com.postech.soat.commons.infrastructure.exception.NotFoundException;
import br.com.postech.soat.customer.core.application.dto.FindCustomerRequest;
import br.com.postech.soat.customer.core.domain.model.Customer;
import br.com.postech.soat.customer.core.ports.in.FindCustomerUseCase;
import br.com.postech.soat.customer.core.ports.out.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FindCustomerService implements FindCustomerUseCase {

    private final CustomerRepository customerRepository;

    @Transactional(readOnly = true)
    @Override
    public Customer execute(FindCustomerRequest request) {
        return customerRepository.findByCpf(request.cpf())
            .orElseThrow(() -> new NotFoundException("Customer not found for the document identifier: " + request.cpf()));
    }
}