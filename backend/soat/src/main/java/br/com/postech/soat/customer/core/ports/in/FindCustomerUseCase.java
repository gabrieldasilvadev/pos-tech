package br.com.postech.soat.customer.core.ports.in;

import br.com.postech.soat.customer.core.application.dto.FindCustomerRequest;
import br.com.postech.soat.customer.core.domain.model.Customer;

public interface FindCustomerUseCase {
    Customer execute(FindCustomerRequest request);
}