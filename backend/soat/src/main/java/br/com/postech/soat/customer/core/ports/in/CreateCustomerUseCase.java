package br.com.postech.soat.customer.core.ports.in;

import br.com.postech.soat.customer.core.domain.model.Customer;

public interface CreateCustomerUseCase {
    Customer create(CreateCustomerCommand command);

    record CreateCustomerCommand(String name, String email, String cpf, String phone) {
    }
}
