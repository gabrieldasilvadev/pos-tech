package br.com.postech.soat.customer.core.ports.out;

import br.com.postech.soat.customer.core.domain.model.Customer;
import java.util.Optional;

public interface CustomerRepository {

    Customer save(Customer customer);

    Optional<Customer> findByCpf(String cpf);
}
