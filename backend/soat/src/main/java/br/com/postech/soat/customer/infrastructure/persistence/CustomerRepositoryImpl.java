package br.com.postech.soat.customer.infrastructure.persistence;

import br.com.postech.soat.commons.infrastructure.aop.monitorable.Monitorable;
import br.com.postech.soat.customer.domain.model.Customer;
import br.com.postech.soat.customer.application.repositories.CustomerRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Monitorable
@Repository
@RequiredArgsConstructor
public class CustomerRepositoryImpl implements CustomerRepository {

    private final Logger logger = LoggerFactory.getLogger(CustomerRepositoryImpl.class);

    private final CustomerJpaRepository customerJpaRepository;
    private final CustomerPersistenceMapper customerPersistenceMapper;

    @Override
    public Customer save(final Customer customer) {
        CustomerEntity customerEntity = customerPersistenceMapper.toEntity(customer);
        customerEntity = customerJpaRepository.save(customerEntity);

        logger.info("Customer saved : {}", customerEntity);

        return customerPersistenceMapper.toModel(customerEntity);
    }

    @Override
    public Optional<Customer> findByCpf(String cpf) {
        var customerEntityOptional = customerJpaRepository.findByCpf(cpf);

        if (customerEntityOptional.isPresent()) {
            var customerEntity = customerEntityOptional.get();
            logger.info("Customer searched : {}", customerEntity);

            return Optional.of(customerPersistenceMapper.toModel(customerEntity));
        }

        return Optional.empty();
    }
}
