package br.com.postech.soat.customer.adapters.in.http;

import br.com.postech.soat.customer.core.application.dto.CreateCustomerRequest;
import br.com.postech.soat.customer.core.application.dto.FindCustomerRequest;
import br.com.postech.soat.customer.core.ports.in.CreateCustomerUseCase;
import br.com.postech.soat.customer.core.ports.in.FindCustomerUseCase;
import br.com.postech.soat.openapi.api.CustomerApi;
import br.com.postech.soat.openapi.model.CreateCustomerRequestDto;
import br.com.postech.soat.openapi.model.FindCustomer200ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CustomerController implements CustomerApi {

    private final CreateCustomerUseCase createCustomerUseCase;
    private final FindCustomerUseCase findCustomerUseCase;
    private final CustomerWebMapper customerWebMapper;

    @Override
    public ResponseEntity<FindCustomer200ResponseDto> createCustomer(@RequestBody CreateCustomerRequestDto createCustomerRequest) {
        CreateCustomerRequest request = customerWebMapper.toRequest(createCustomerRequest);

        final var customer = createCustomerUseCase.execute(request);

        return ResponseEntity.status(HttpStatus.CREATED)
            .body(customerWebMapper.toResponse(customer));
    }

    @Override
    public ResponseEntity<FindCustomer200ResponseDto> findCustomer(String cpf) {
        FindCustomerRequest request = new FindCustomerRequest(cpf);
        var customer = findCustomerUseCase.execute(request);
        return ResponseEntity.ok(customerWebMapper.toResponse(customer));
    }
}