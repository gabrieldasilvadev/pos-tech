package br.com.postech.soat.customer.infrastructure.http;

import br.com.postech.soat.customer.application.repositories.CustomerRepository;
import br.com.postech.soat.customer.domain.model.Customer;
import br.com.postech.soat.openapi.model.CreateCustomerRequestDto;
import br.com.postech.soat.openapi.model.FindCustomer200ResponseDto;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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
    private CustomerRepository customerRepository;

    private CustomerController customerController;

    @BeforeEach
    void setUp() {
        customerController = new CustomerController(customerRepository);
    }

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

            Customer customer = Customer.create(
                new br.com.postech.soat.customer.domain.valueobject.Name("John Doe"),
                new br.com.postech.soat.customer.domain.valueobject.Email("john.doe@example.com"),
                new br.com.postech.soat.customer.domain.valueobject.CPF("12345678901"),
                new br.com.postech.soat.customer.domain.valueobject.Phone("11987654321")
            );

            FindCustomer200ResponseDto expectedResponse = new FindCustomer200ResponseDto();
            expectedResponse.setId(customer.getId().value().toString());
            expectedResponse.setName(customer.getName().value());
            expectedResponse.setEmail(customer.getEmail().value());
            expectedResponse.setCpf(customer.getCpf().value());
            expectedResponse.setPhone(customer.getPhone().value());

            // Mock repository behavior
            when(customerRepository.exists("12345678901", "john.doe@example.com", "11987654321")).thenReturn(false);
            when(customerRepository.save(any(Customer.class))).thenReturn(customer);

            // Act
            ResponseEntity<FindCustomer200ResponseDto> response = customerController.createCustomer(request);

            // Assert
            assertNotNull(response);
            assertEquals(HttpStatus.CREATED, response.getStatusCode());
            assertEquals(expectedResponse, response.getBody());

            verify(customerRepository).exists("12345678901", "john.doe@example.com", "11987654321");
            verify(customerRepository).save(any(Customer.class));
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

            Customer customer = Customer.create(
                new br.com.postech.soat.customer.domain.valueobject.Name("John Doe"),
                new br.com.postech.soat.customer.domain.valueobject.Email("john.doe@example.com"),
                new br.com.postech.soat.customer.domain.valueobject.CPF(cpf),
                new br.com.postech.soat.customer.domain.valueobject.Phone("11987654321")
            );

            FindCustomer200ResponseDto expectedResponse = new FindCustomer200ResponseDto();
            expectedResponse.setId(customer.getId().value().toString());
            expectedResponse.setName(customer.getName().value());
            expectedResponse.setEmail(customer.getEmail().value());
            expectedResponse.setCpf(customer.getCpf().value());
            expectedResponse.setPhone(customer.getPhone().value());

            // Mock repository behavior
            when(customerRepository.findByCpf(cpf)).thenReturn(Optional.of(customer));

            // Act
            ResponseEntity<FindCustomer200ResponseDto> response = customerController.findCustomer(cpf);

            // Assert
            assertNotNull(response);
            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertEquals(expectedResponse, response.getBody());

            verify(customerRepository).findByCpf(cpf);
        }

        @Test
        @DisplayName("Should pass correct query to use case")
        void givenCpf_whenFindCustomer_thenCreateCorrectQuery() {
            // Arrange
            String cpf = "12345678901";

            Customer customer = Customer.create(
                new br.com.postech.soat.customer.domain.valueobject.Name("John Doe"),
                new br.com.postech.soat.customer.domain.valueobject.Email("john.doe@example.com"),
                new br.com.postech.soat.customer.domain.valueobject.CPF(cpf),
                new br.com.postech.soat.customer.domain.valueobject.Phone("11987654321")
            );

            FindCustomer200ResponseDto response = new FindCustomer200ResponseDto();

            // Mock repository behavior
            when(customerRepository.findByCpf(cpf)).thenReturn(Optional.of(customer));

            // Act
            customerController.findCustomer(cpf);

            // Assert - Verify repository was called with correct CPF
            verify(customerRepository).findByCpf(cpf);
        }
    }
}