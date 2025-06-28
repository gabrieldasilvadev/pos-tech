package br.com.postech.soat.customer.core.application;

import br.com.postech.soat.commons.infrastructure.exception.ResourceConflictException;
import br.com.postech.soat.customer.core.application.dto.CreateCustomerRequest;
import br.com.postech.soat.customer.core.application.services.CreateCustomerService;
import br.com.postech.soat.customer.core.domain.exception.InvalidCpfException;
import br.com.postech.soat.customer.core.domain.exception.InvalidEmailException;
import br.com.postech.soat.customer.core.domain.exception.InvalidNameException;
import br.com.postech.soat.customer.core.domain.exception.InvalidPhoneException;
import br.com.postech.soat.customer.core.domain.model.Customer;
import br.com.postech.soat.customer.core.domain.valueobject.CPF;
import br.com.postech.soat.customer.core.domain.valueobject.CustomerId;
import br.com.postech.soat.customer.core.domain.valueobject.Email;
import br.com.postech.soat.customer.core.domain.valueobject.Name;
import br.com.postech.soat.customer.core.domain.valueobject.Phone;
import br.com.postech.soat.customer.core.ports.in.CreateCustomerUseCase;
import br.com.postech.soat.customer.core.ports.out.CustomerRepository;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Tests for CreateCustomerUseCase")
class CreateCustomerUseCaseTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CreateCustomerService createCustomerUseCase;

    @Test
    @DisplayName("Should create a customer successfully when valid data is provided")
    void givenValidCommand_whenCreate_thenReturnCustomer() {
        // Arrange
        CreateCustomerRequest request = new CreateCustomerRequest(
            "João Silva",
            "joao@example.com",
            "12345678901",
            "11987654321"
        );

        Customer expectedCustomer = Customer.create(
            new Name("João Silva"),
            new Email("joao@example.com"),
            new CPF("12345678901"),
            new Phone("11987654321")
        );

        when(customerRepository.findByCpf("12345678901")).thenReturn(Optional.empty());
        when(customerRepository.save(any(Customer.class))).thenReturn(expectedCustomer);

        // Act
        Customer result = createCustomerUseCase.execute(request);

        // Assert
        assertNotNull(result);
        assertEquals(expectedCustomer.getName().value(), result.getName().value());
        assertEquals(expectedCustomer.getEmail().value(), result.getEmail().value());
        assertEquals(expectedCustomer.getCpf().value(), result.getCpf().value());
        assertEquals(expectedCustomer.getPhone().value(), result.getPhone().value());

        verify(customerRepository).findByCpf("12345678901");
        verify(customerRepository).save(any(Customer.class));
    }

    @Test
    @DisplayName("Should throw ResourceConflictException when CPF already exists")
    void givenExistingCpf_whenCreate_thenThrowResourceConflictException() {
        // Arrange
        CreateCustomerRequest request = new CreateCustomerRequest(
            "João Silva",
            "joao@example.com",
            "12345678901",
            "11987654321"
        );

        Customer existingCustomer = Customer.create(
            new Name("Cliente Existente"),
            new Email("existente@example.com"),
            new CPF("12345678901"),
            new Phone("11999999999")
        );

        when(customerRepository.findByCpf("12345678901")).thenReturn(Optional.of(existingCustomer));

        // Act & Assert
        ResourceConflictException exception = assertThrows(
            ResourceConflictException.class,
            () -> createCustomerUseCase.execute(request)
        );

        assertEquals("Customer with document identifier: 12345678901, already exists", exception.getMessage());
        verify(customerRepository).findByCpf("12345678901");
    }

    @Test
    @DisplayName("Should throw InvalidCpfException when invalid CPF is provided")
    void givenInvalidCpf_whenCreate_thenThrowInvalidCpfException() {
        // Arrange
        CreateCustomerRequest request = new CreateCustomerRequest(
            "João Silva",
            "joao@example.com",
            "123", // CPF inválido (muito curto)
            "11987654321"
        );

        // Act & Assert
        assertThrows(
            InvalidCpfException.class,
            () -> createCustomerUseCase.execute(request)
        );
    }

    @Test
    @DisplayName("Should throw InvalidEmailException when invalid email is provided")
    void givenInvalidEmail_whenCreate_thenThrowInvalidEmailException() {
        // Arrange
        CreateCustomerRequest request = new CreateCustomerRequest(
            "João Silva",
            "email-invalido", // Email inválido
            "12345678901",
            "11987654321"
        );

        // Act & Assert
        assertThrows(
            InvalidEmailException.class,
            () -> createCustomerUseCase.execute(request)
        );
    }

    @Test
    @DisplayName("Should throw InvalidNameException when invalid name is provided")
    void givenInvalidName_whenCreate_thenThrowInvalidNameException() {
        // Arrange
        CreateCustomerRequest request = new CreateCustomerRequest(
            "", // Nome inválido (vazio)
            "joao@example.com",
            "12345678901",
            "11987654321"
        );

        // Act & Assert
        assertThrows(
            InvalidNameException.class,
            () -> createCustomerUseCase.execute(request)
        );
    }

    @Test
    @DisplayName("Should throw InvalidPhoneException when invalid phone is provided")
    void givenInvalidPhone_whenCreate_thenThrowInvalidPhoneException() {
        // Arrange
        CreateCustomerRequest request = new CreateCustomerRequest(
            "João Silva",
            "joao@example.com",
            "12345678901",
            "123" // Telefone inválido (muito curto)
        );

        // Act & Assert
        assertThrows(
            InvalidPhoneException.class,
            () -> createCustomerUseCase.execute(request)
        );
    }
}

