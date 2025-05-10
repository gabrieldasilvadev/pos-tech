package br.com.postech.soat.customer.core.ports.out;

import br.com.postech.soat.customer.core.domain.valueobject.CPF;
import br.com.postech.soat.openapi.model.Customer;
import java.util.List;

public interface CustomerRepository {

    Customer save(Customer customer);
    List<Customer> find(CPF cpf);
    boolean existsByCpf(CPF cpf);
}
