package br.com.postech.soat.customer.adapters.in.http;

import br.com.postech.soat.customer.core.application.dto.CreateCustomerCommand;
import br.com.postech.soat.customer.core.domain.model.Customer;
import br.com.postech.soat.openapi.model.CreateCustomerRequestDto;
import br.com.postech.soat.openapi.model.FindCustomer200ResponseDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CustomerWebMapper {

    CreateCustomerCommand toCommand(CreateCustomerRequestDto request);

    FindCustomer200ResponseDto toResponse(Customer customer);
}
