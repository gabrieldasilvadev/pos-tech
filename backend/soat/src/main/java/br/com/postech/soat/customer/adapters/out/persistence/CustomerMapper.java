package br.com.postech.soat.customer.adapters.out.persistence;

import br.com.postech.soat.customer.core.domain.model.Customer;
import br.com.postech.soat.customer.core.domain.valueobject.CPF;
import br.com.postech.soat.customer.core.domain.valueobject.CustomerId;
import br.com.postech.soat.customer.core.domain.valueobject.Email;
import br.com.postech.soat.customer.core.domain.valueobject.Name;
import br.com.postech.soat.customer.core.domain.valueobject.Phone;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", imports = {CustomerId.class, Name.class, CPF.class, Email.class, Phone.class})
public interface CustomerMapper {

    @Mapping(target = "id", expression = "java(customer.getId().value())")
    @Mapping(target = "name", expression = "java(customer.getName().value())")
    @Mapping(target = "cpf", expression = "java(customer.getCpf().value())")
    @Mapping(target = "email", expression = "java(customer.getEmail().value())")
    @Mapping(target = "phone", expression = "java(customer.getPhone().value())")
    CustomerEntity toEntity(Customer customer);

    @Mapping(target = "id", expression = "java(new CustomerId(customerEntity.getId()))")
    @Mapping(target = "name", expression = "java(new Name(customerEntity.getName()))")
    @Mapping(target = "cpf", expression = "java(new CPF(customerEntity.getCpf()))")
    @Mapping(target = "email", expression = "java(new Email(customerEntity.getEmail()))")
    @Mapping(target = "phone", expression = "java(new Phone(customerEntity.getPhone()))")
    Customer toModel(CustomerEntity customerEntity);
}
