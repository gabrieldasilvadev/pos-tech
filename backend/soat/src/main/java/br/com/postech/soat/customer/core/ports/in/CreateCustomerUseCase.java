package br.com.postech.soat.customer.core.ports.in;

import br.com.postech.soat.customer.core.application.dto.CreateCustomerRequest;
import br.com.postech.soat.customer.core.domain.model.Customer;

public interface CreateCustomerUseCase {
    Customer execute(CreateCustomerRequest request);
}