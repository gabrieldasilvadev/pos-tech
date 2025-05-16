package br.com.postech.soat.customer.core.ports.in;

import br.com.postech.soat.customer.core.domain.model.Customer;
import br.com.postech.soat.customer.core.domain.valueobject.CPF;

public interface FindCustomerUseCase {
    Customer findByCpf(CPF cpf);
}
