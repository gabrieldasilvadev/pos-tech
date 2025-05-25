package br.com.postech.soat.customer.adapters.in.http;

import br.com.postech.soat.customer.core.application.dto.CreateCustomerCommand;
import br.com.postech.soat.customer.core.application.dto.FindCustomerQuery;
import br.com.postech.soat.customer.core.domain.model.Customer;
import br.com.postech.soat.customer.core.ports.in.CreateCustomerUseCase;
import br.com.postech.soat.customer.core.ports.in.FindCustomerUseCase;
import br.com.postech.soat.openapi.api.CustomerApi;
import br.com.postech.soat.openapi.model.CreateCustomerRequestDto;
import br.com.postech.soat.openapi.model.FindCustomer200ResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
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
    public ResponseEntity<FindCustomer200ResponseDto> createCustomer(@RequestBody CreateCustomerRequestDto createCustomerRequest) {
        CreateCustomerCommand command = customerWebMapper.toCommand(createCustomerRequest);

        final var customer = createCustomerUseCase.create(command);

        return ResponseEntity.status(HttpStatus.CREATED)
            .body(customerWebMapper.toResponse(customer));
    }

    @Override
    public ResponseEntity<FindCustomer200ResponseDto> findCustomer(String cpf) {
        FindCustomerQuery query = new FindCustomerQuery(cpf);
        Customer customer = findCustomerUseCase.findByCpf(query);
        return ResponseEntity.ok(customerWebMapper.toResponse(customer));
    }
}