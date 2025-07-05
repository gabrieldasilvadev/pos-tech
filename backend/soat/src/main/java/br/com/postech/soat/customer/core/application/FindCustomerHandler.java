package br.com.postech.soat.customer.core.application;

import br.com.postech.soat.commons.infrastructure.exception.NotFoundException;
import br.com.postech.soat.customer.core.application.dto.FindCustomerQuery;
import br.com.postech.soat.customer.core.domain.model.Customer;
import br.com.postech.soat.customer.core.domain.valueobject.CPF;
import br.com.postech.soat.customer.core.ports.in.FindCustomerUseCase;
import br.com.postech.soat.customer.core.ports.out.CustomerRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class FindCustomerHandler implements FindCustomerUseCase {

    private final CustomerRepository customerRepository;

    @Override
    public Customer execute(FindCustomerQuery query) {
        CPF cpf = new CPF(query.cpf());
        return customerRepository.findByCpf(cpf.value())
            .orElseThrow(() -> new NotFoundException("Customer not found for the document identifier: " + cpf.value()));
    }
}
