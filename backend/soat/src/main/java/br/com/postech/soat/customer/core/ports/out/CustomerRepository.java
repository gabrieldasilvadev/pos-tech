package br.com.postech.soat.customer.core.ports.out;

import br.com.postech.soat.customer.core.domain.model.Customer;
import br.com.postech.soat.customer.core.domain.valueobject.CPF;

public interface CustomerRepository {

    Customer save(Customer customer);
    Customer findByCpf(CPF cpf);
    boolean existsByCpf(CPF cpf);
}
