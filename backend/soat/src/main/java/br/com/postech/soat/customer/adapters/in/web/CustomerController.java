package br.com.postech.soat.customer.adapters.in.web;

import br.com.postech.soat.customer.core.domain.model.Customer;
import br.com.postech.soat.customer.core.ports.in.CreateCustomerUseCase;
import br.com.postech.soat.customer.core.ports.in.CreateCustomerUseCase.CreateCustomerCommand;
import br.com.postech.soat.customer.core.ports.in.FindCustomerUseCase;
import br.com.postech.soat.customer.core.ports.in.FindCustomerUseCase.FindCustomerQuery;
import br.com.postech.soat.openapi.api.CustomerApi;
import br.com.postech.soat.openapi.model.CreateCustomerRequest;
import br.com.postech.soat.openapi.model.FindCustomer200Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomerController implements CustomerApi {

    private final FindCustomerUseCase findCustomerUseCase;
    private final CreateCustomerUseCase createCustomerUseCase;
    private final CustomerWebMapper customerWebMapper;

    public CustomerController(FindCustomerUseCase findCustomerUseCase,
                              CreateCustomerUseCase createCustomerUseCase,
                              CustomerWebMapper customerWebMapper) {
        this.findCustomerUseCase = findCustomerUseCase;
        this.createCustomerUseCase = createCustomerUseCase;
        this.customerWebMapper = customerWebMapper;
    }

    @Override
    public ResponseEntity<FindCustomer200Response> createCustomer(CreateCustomerRequest createCustomerRequest) {
        if (createCustomerRequest == null) {
            return ResponseEntity.badRequest().build();
        }

        CreateCustomerCommand command = customerWebMapper.toCommand(createCustomerRequest);

        var customer = createCustomerUseCase.create(command);

        return ResponseEntity.status(HttpStatus.CREATED)
            .body(customerWebMapper.toResponse(customer));
    }

    @Override
    public ResponseEntity<FindCustomer200Response> findCustomer(String cpf) {
        FindCustomerQuery query = new FindCustomerQuery(cpf);
        Customer customer = findCustomerUseCase.findByCpf(query);
        return ResponseEntity.ok(customerWebMapper.toResponse(customer));
    }
}