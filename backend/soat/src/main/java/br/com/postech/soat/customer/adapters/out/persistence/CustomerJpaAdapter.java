package br.com.postech.soat.customer.adapters.out.persistence;

import br.com.postech.soat.commons.infrastructure.aop.monitorable.Monitorable;
import br.com.postech.soat.customer.core.domain.model.Customer;
import br.com.postech.soat.customer.core.ports.out.CustomerRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Monitorable
@Repository
@RequiredArgsConstructor
public class CustomerJpaAdapter implements CustomerRepository {

    private final Logger logger = LoggerFactory.getLogger(CustomerJpaAdapter.class);

    private final CustomerJpaRepository customerJpaRepository;
    private final CustomerMapper customerMapper;

    @Override
    public Customer save(final Customer customer) {
        CustomerEntity customerEntity = customerMapper.toEntity(customer);
        customerEntity = customerJpaRepository.save(customerEntity);

        logger.info("Customer saved : {}", customerEntity);

        return customerMapper.toModel(customerEntity);
    }

    @Override
    public Optional<Customer> findByCpf(String cpf) {
        var customerEntityOptional = customerJpaRepository.findByCpf(cpf);

        if (customerEntityOptional.isPresent()) {
            var customerEntity = customerEntityOptional.get();
            logger.info("Customer searched : {}", customerEntity);

            return Optional.of(customerMapper.toModel(customerEntity));
        }

        return Optional.empty();
    }

    @Override
    public boolean existsByCpf(String cpf) {
        return customerJpaRepository.existsByCpf(cpf);
    }

    @Override
    public boolean existsByEmail(String email) {
        return customerJpaRepository.existsByEmail(email);
    }

    @Override
    public boolean existsByPhone(String phone) {
        return customerJpaRepository.existsByPhone(phone);
    }
}
