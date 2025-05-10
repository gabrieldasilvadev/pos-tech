package br.com.postech.soat.customer.adapters.in.web;

import br.com.postech.soat.customer.core.domain.valueobject.CPF;
import br.com.postech.soat.customer.core.domain.valueobject.Email;
import br.com.postech.soat.customer.core.domain.valueobject.Name;
import br.com.postech.soat.customer.core.ports.in.CreateCustomerUseCase;
import br.com.postech.soat.customer.core.ports.in.FindCustomerUseCase;
import br.com.postech.soat.openapi.api.CustomerApi;
import br.com.postech.soat.openapi.model.Customer;
import java.util.List;
import br.com.postech.soat.openapi.model.PostClientsRequest;
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
    public ResponseEntity<List<Customer>> getClients(String cpf) {
        List<Customer> customers = findCustomerUseCase.find(cpf != null ? new CPF(cpf) : null);

        if (customers.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(customers);
    }

    @Override
    public ResponseEntity<Void> postClients(PostClientsRequest request) {
        if (request == null) {
            return ResponseEntity.badRequest().build();
        }

        CPF cpf = new CPF(request.getCpf());
        Name name = new Name(request.getName());
        Email email = new Email(request.getEmail());

        createCustomerUseCase.create(cpf, name, email);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}