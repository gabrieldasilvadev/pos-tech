package br.com.postech.soat.customer.adapters.out.persistente;

import br.com.postech.soat.customer.core.domain.valueobject.CPF;
import br.com.postech.soat.customer.core.ports.out.CustomerRepository;
import br.com.postech.soat.openapi.model.Customer;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class CustomerJpaAdapter implements CustomerRepository {

    private final CustomerJpaRepository customerJpaRepository;

    public CustomerJpaAdapter(CustomerJpaRepository customerJpaRepository) {
        this.customerJpaRepository = customerJpaRepository;
    }

    @Override
    public Customer save(Customer customer) {
        CustomerEntity entity = toEntity(customer);
        CustomerEntity savedEntity = customerJpaRepository.save(entity);
        return toDomain(savedEntity);
    }

    @Override
    public List<Customer> find(CPF cpf) {
        return customerJpaRepository.find(cpf != null ? cpf.value() : null)
            .stream()
            .map(this::toDomain)
            .toList();
    }

    @Override
    public boolean existsByCpf(CPF cpf) {
        return customerJpaRepository.existsByCpf(cpf.value());
    }

    private CustomerEntity toEntity(Customer customer) {
        CustomerEntity entity = new CustomerEntity();
        entity.setId(customer.getId());
        entity.setCpf(customer.getCpf());
        entity.setName(customer.getName());
        entity.setEmail(customer.getEmail());
        return entity;
    }

    private Customer toDomain(CustomerEntity entity) {
        Customer customer = new Customer();
        customer.setId(entity.getId());
        customer.setCpf(entity.getCpf());
        customer.setName(entity.getName());
        customer.setEmail(entity.getEmail());
        return customer;
    }
}
