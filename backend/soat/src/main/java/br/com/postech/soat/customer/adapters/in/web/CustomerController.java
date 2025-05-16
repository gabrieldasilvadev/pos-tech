package br.com.postech.soat.customer.adapters.in.web;

import br.com.postech.soat.customer.core.domain.model.Customer;
import br.com.postech.soat.customer.core.domain.valueobject.CPF;
import br.com.postech.soat.customer.core.domain.valueobject.Email;
import br.com.postech.soat.customer.core.domain.valueobject.Name;
import br.com.postech.soat.customer.core.domain.valueobject.Phone;
import br.com.postech.soat.customer.core.ports.in.CreateCustomerUseCase;
import br.com.postech.soat.customer.core.ports.in.FindCustomerUseCase;
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

    public CustomerController(FindCustomerUseCase findCustomerUseCase, CreateCustomerUseCase createCustomerUseCase) {
        this.findCustomerUseCase = findCustomerUseCase;
        this.createCustomerUseCase = createCustomerUseCase;
    }

    @Override
    public ResponseEntity<FindCustomer200Response> createCustomer(CreateCustomerRequest createCustomerRequest) {
        if (createCustomerRequest == null) {
            return ResponseEntity.badRequest().build();
        }

        CPF cpf = new CPF(createCustomerRequest.getCpf());
        Name name = new Name(createCustomerRequest.getName());
        Email email = new Email(createCustomerRequest.getEmail());
        Phone phone = new Phone(createCustomerRequest.getPhone());

        var customer = createCustomerUseCase.create(cpf, name, email, phone);

        return ResponseEntity.status(HttpStatus.CREATED)
            .body(toFindCustomer200Response(customer));
    }

    @Override
    public ResponseEntity<FindCustomer200Response> findCustomer(String cpf) {
        Customer customer = findCustomerUseCase.findByCpf(new CPF(cpf));
        return ResponseEntity.ok(toFindCustomer200Response(customer));
    }

    private FindCustomer200Response toFindCustomer200Response(Customer customer) {
        FindCustomer200Response response = new FindCustomer200Response();
        response.setId(customer.getId().toString());
        response.setCpf(customer.getCpf());
        response.setName(customer.getName());
        response.setEmail(customer.getEmail());
        response.setPhone(customer.getPhone());
        return response;
    }
}