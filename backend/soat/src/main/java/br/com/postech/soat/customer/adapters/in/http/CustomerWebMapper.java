package br.com.postech.soat.customer.adapters.in.http;

import br.com.postech.soat.customer.core.application.dto.CreateCustomerRequest;
import br.com.postech.soat.customer.core.domain.model.Customer;
import br.com.postech.soat.openapi.model.CreateCustomerRequestDto;
import br.com.postech.soat.openapi.model.FindCustomer200ResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CustomerWebMapper {

    CreateCustomerRequest toRequest(CreateCustomerRequestDto requestDto);

    @Mapping(target = "id", expression = "java(customer.getId().value().toString())")
    @Mapping(target = "name", expression = "java(customer.getName().value())")
    @Mapping(target = "cpf", expression = "java(customer.getCpf().value())")
    @Mapping(target = "email", expression = "java(customer.getEmail().value())")
    @Mapping(target = "phone", expression = "java(customer.getPhone().value())")
    FindCustomer200ResponseDto toResponse(Customer customer);
}
