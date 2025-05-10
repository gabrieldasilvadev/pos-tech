package br.com.postech.soat.customer.core.ports.in;

import br.com.postech.soat.customer.core.domain.valueobject.CPF;
import br.com.postech.soat.openapi.model.Customer;
import java.util.List;

public interface FindCustomerUseCase {
    List<Customer> find(CPF cpf);
}
