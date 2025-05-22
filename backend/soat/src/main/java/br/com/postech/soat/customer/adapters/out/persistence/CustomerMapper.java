package br.com.postech.soat.customer.adapters.out.persistence;


import br.com.postech.soat.customer.core.domain.model.Customer;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    CustomerEntity toEntity(Customer customer);

    Customer toModel(CustomerEntity customerEntity);
}
