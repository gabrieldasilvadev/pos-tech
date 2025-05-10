package br.com.postech.soat.customer.core.ports.in;

import br.com.postech.soat.customer.core.domain.valueobject.CPF;
import br.com.postech.soat.customer.core.domain.valueobject.Email;
import br.com.postech.soat.customer.core.domain.valueobject.Name;
import br.com.postech.soat.openapi.model.Customer;

public interface CreateCustomerUseCase {
    Customer create(CPF cpf, Name name, Email email);
}
