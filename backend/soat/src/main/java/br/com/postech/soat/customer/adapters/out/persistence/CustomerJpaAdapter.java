package br.com.postech.soat.customer.adapters.out.persistence;

import br.com.postech.soat.customer.core.domain.model.Customer;
import br.com.postech.soat.customer.core.ports.out.CustomerRepository;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public class CustomerJpaAdapter implements CustomerRepository {

    private final CustomerJpaRepository customerJpaRepository;
    private final CustomerMapper customerMapper;

    public CustomerJpaAdapter(CustomerJpaRepository customerJpaRepository,
                              CustomerMapper customerMapper) {
        this.customerJpaRepository = customerJpaRepository;
        this.customerMapper = customerMapper;
    }

    @Override
    public Customer save(final Customer customer) {
        CustomerEntity entity = customerMapper.toEntity(customer);
        entity = customerJpaRepository.save(entity);

        return customerMapper.toModel(entity);
    }

    @Override
    public Optional<Customer> findByCpf(String cpf) {
        var customerEntityOptional = customerJpaRepository.findByCpf(cpf);
        return customerEntityOptional.map(customerMapper::toModel);
    }
}
