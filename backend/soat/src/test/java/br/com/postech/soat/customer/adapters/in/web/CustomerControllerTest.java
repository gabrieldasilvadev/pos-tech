package br.com.postech.soat.customer.adapters.in.web;

import br.com.postech.soat.customer.adapters.in.http.CustomerController;
import br.com.postech.soat.customer.adapters.in.http.CustomerWebMapper;
import br.com.postech.soat.customer.core.application.dto.CreateCustomerCommand;
import br.com.postech.soat.customer.core.application.dto.FindCustomerQuery;
import br.com.postech.soat.customer.core.domain.model.Customer;
import br.com.postech.soat.customer.core.ports.in.CreateCustomerUseCase;
import br.com.postech.soat.customer.core.ports.in.FindCustomerUseCase;
import br.com.postech.soat.openapi.model.CreateCustomerRequestDto;
import br.com.postech.soat.openapi.model.FindCustomer200ResponseDto;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Customer Controller Tests")
class CustomerControllerTest {

    @Mock
    private FindCustomerUseCase findCustomerUseCase;

    @Mock
    private CreateCustomerUseCase createCustomerUseCase;

    @Mock
    private CustomerWebMapper customerWebMapper;

    @InjectMocks
    private CustomerController customerController;

    @Nested
    @DisplayName("Tests for the customer creation api")
    class CreateCustomer {

        @Test
        @DisplayName("Should create customer and return created status")
        void givenValidRequest_whenCreateCustomer_thenReturnCreatedWithCustomer() {
            // Arrange
            CreateCustomerRequestDto request = new CreateCustomerRequestDto();
            request.setName("John Doe");
            request.setEmail("john.doe@example.com");
            request.setCpf("12345678901");
            request.setPhone("11987654321");

            CreateCustomerCommand command = new CreateCustomerCommand(
                "John Doe",
                "john.doe@example.com",
                "12345678901",
                "11987654321"
            );

            Customer customer = Customer.builder()
                .id(UUID.randomUUID())
                .name("John Doe")
                .email("john.doe@example.com")
                .cpf("12345678901")
                .phone("11987654321")
                .build();

            FindCustomer200ResponseDto expectedResponse = new FindCustomer200ResponseDto();
            expectedResponse.setId(customer.getId().toString());
            expectedResponse.setName(customer.getName());
            expectedResponse.setEmail(customer.getEmail());
            expectedResponse.setCpf(customer.getCpf());
            expectedResponse.setPhone(customer.getPhone());

            when(customerWebMapper.toCommand(request)).thenReturn(command);
            when(createCustomerUseCase.create(command)).thenReturn(customer);
            when(customerWebMapper.toResponse(customer)).thenReturn(expectedResponse);

            // Act
            ResponseEntity<FindCustomer200ResponseDto> response = customerController.createCustomer(request);

            // Assert
            assertNotNull(response);
            assertEquals(HttpStatus.CREATED, response.getStatusCode());
            assertEquals(expectedResponse, response.getBody());

            verify(customerWebMapper).toCommand(request);
            verify(createCustomerUseCase).create(command);
            verify(customerWebMapper).toResponse(customer);
        }
    }

    @Nested
    @DisplayName("Tests for customer search api by cpf")
    class FindCustomer {

        @Test
        @DisplayName("Should find customer by CPF and return OK status")
        void givenValidCpf_whenFindCustomer_thenReturnOkWithCustomer() {
            // Arrange
            String cpf = "12345678901";

            Customer customer = Customer.builder()
                .id(UUID.randomUUID())
                .name("John Doe")
                .email("john.doe@example.com")
                .cpf(cpf)
                .phone("11987654321")
                .build();

            FindCustomer200ResponseDto expectedResponse = new FindCustomer200ResponseDto();
            expectedResponse.setId(customer.getId().toString());
            expectedResponse.setName(customer.getName());
            expectedResponse.setEmail(customer.getEmail());
            expectedResponse.setCpf(customer.getCpf());
            expectedResponse.setPhone(customer.getPhone());

            when(findCustomerUseCase.findByCpf(any(FindCustomerQuery.class))).thenReturn(customer);
            when(customerWebMapper.toResponse(customer)).thenReturn(expectedResponse);

            // Act
            ResponseEntity<FindCustomer200ResponseDto> response = customerController.findCustomer(cpf);

            // Assert
            assertNotNull(response);
            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertEquals(expectedResponse, response.getBody());

            verify(findCustomerUseCase).findByCpf(any(FindCustomerQuery.class));
            verify(customerWebMapper).toResponse(customer);
        }

        @Test
        @DisplayName("Should pass correct query to use case")
        void givenCpf_whenFindCustomer_thenCreateCorrectQuery() {
            // Arrange
            String cpf = "12345678901";

            Customer customer = Customer.builder()
                .id(UUID.randomUUID())
                .name("John Doe")
                .email("john.doe@example.com")
                .cpf(cpf)
                .phone("11987654321")
                .build();

            FindCustomer200ResponseDto response = new FindCustomer200ResponseDto();

            when(findCustomerUseCase.findByCpf(any(FindCustomerQuery.class))).thenReturn(customer);
            when(customerWebMapper.toResponse(customer)).thenReturn(response);

            // Act
            customerController.findCustomer(cpf);

            // Assert - Here we use a capture to verify the exact query that was passed
            ArgumentCaptor<FindCustomerQuery> queryCaptor = ArgumentCaptor.forClass(FindCustomerQuery.class);
            verify(findCustomerUseCase).findByCpf(queryCaptor.capture());

            FindCustomerQuery capturedQuery = queryCaptor.getValue();
            assertEquals(cpf, capturedQuery.cpf());
        }
    }
}