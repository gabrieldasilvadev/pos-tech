package br.com.postech.soat.customer.application.repositories;

import br.com.postech.soat.customer.domain.model.Customer;
import java.util.Optional;

public interface CustomerRepository {

    Customer save(Customer customer);

    Optional<Customer> findByCpf(String cpf);
}
