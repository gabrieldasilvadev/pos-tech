package br.com.postech.soat.customer.adapters.in.web;

import br.com.postech.soat.customer.core.domain.model.Customer;
import br.com.postech.soat.customer.core.ports.in.CreateCustomerUseCase.CreateCustomerCommand;
import br.com.postech.soat.openapi.model.CreateCustomerRequest;
import br.com.postech.soat.openapi.model.FindCustomer200Response;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CustomerWebMapper {

    CreateCustomerCommand toCommand(CreateCustomerRequest request);

    FindCustomer200Response toResponse(Customer customer);
}
