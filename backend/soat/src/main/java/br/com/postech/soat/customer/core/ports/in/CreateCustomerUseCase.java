package br.com.postech.soat.customer.core.ports.in;

import br.com.postech.soat.customer.core.application.dto.CreateCustomerCommand;
import br.com.postech.soat.customer.core.domain.model.Customer;

public interface CreateCustomerUseCase {

    Customer create(CreateCustomerCommand command);

}
