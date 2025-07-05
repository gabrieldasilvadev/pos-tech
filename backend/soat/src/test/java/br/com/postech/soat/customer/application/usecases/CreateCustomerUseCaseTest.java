package br.com.postech.soat.customer.application.usecases;

import br.com.postech.soat.commons.infrastructure.exception.ResourceConflictException;
import br.com.postech.soat.customer.application.dto.CreateCustomerDto;
import br.com.postech.soat.customer.domain.exception.InvalidCpfException;
import br.com.postech.soat.customer.domain.exception.InvalidEmailException;
import br.com.postech.soat.customer.domain.exception.InvalidNameException;
import br.com.postech.soat.customer.domain.exception.InvalidPhoneException;
import br.com.postech.soat.customer.domain.model.Customer;
import br.com.postech.soat.customer.application.repositories.CustomerRepository;
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
    private CreateCustomerUseCase createCustomerUseCase;

    @Test
    @DisplayName("Should create a customer successfully when valid data is provided")
    void givenValidCommand_whenCreate_thenReturnCustomer() {
        // Arrange
        CreateCustomerDto command = new CreateCustomerDto(
            "João Silva",
            "joao@example.com",
            "12345678901",
            "11987654321"
        );

        when(customerRepository.findByCpf("12345678901")).thenReturn(Optional.empty());
        when(customerRepository.save(any(Customer.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Customer result = createCustomerUseCase.execute(command);

        // Assert
        assertNotNull(result);
        assertEquals("João Silva", result.getName().value());
        assertEquals("joao@example.com", result.getEmail().value());
        assertEquals("12345678901", result.getCpf().value());
        assertEquals("11987654321", result.getPhone().value());

        verify(customerRepository).findByCpf("12345678901");
        verify(customerRepository).save(any(Customer.class));
    }

    @Test
    @DisplayName("Should throw ResourceConflictException when CPF already exists")
    void givenExistingCpf_whenCreate_thenThrowResourceConflictException() {
        // Arrange
        CreateCustomerDto command = new CreateCustomerDto(
            "João Silva",
            "joao@example.com",
            "12345678901",
            "11987654321"
        );

        Customer existingCustomer = Customer.create(
            new br.com.postech.soat.customer.domain.valueobject.Name("Existing Customer"),
            new br.com.postech.soat.customer.domain.valueobject.Email("existing@example.com"),
            new br.com.postech.soat.customer.domain.valueobject.CPF("12345678901"),
            new br.com.postech.soat.customer.domain.valueobject.Phone("11999999999")
        );
        when(customerRepository.findByCpf("12345678901")).thenReturn(Optional.of(existingCustomer));

        // Act & Assert
        ResourceConflictException exception = assertThrows(
            ResourceConflictException.class,
            () -> createCustomerUseCase.execute(command)
        );

        assertEquals("Customer with document identifier: 12345678901, already exists", exception.getMessage());
        verify(customerRepository).findByCpf("12345678901");
    }

    @Test
    @DisplayName("Should throw InvalidCpfException when invalid CPF is provided")
    void givenInvalidCpf_whenCreate_thenThrowInvalidCpfException() {
        // Arrange
        CreateCustomerDto command = new CreateCustomerDto(
            "João Silva",
            "joao@example.com",
            "123", // CPF inválido (muito curto)
            "11987654321"
        );

        // Act & Assert
        assertThrows(
            InvalidCpfException.class,
            () -> createCustomerUseCase.execute(command)
        );
    }

    @Test
    @DisplayName("Should throw InvalidEmailException when invalid email is provided")
    void givenInvalidEmail_whenCreate_thenThrowInvalidEmailException() {
        // Arrange
        CreateCustomerDto command = new CreateCustomerDto(
            "João Silva",
            "email-invalido", // Email inválido
            "12345678901",
            "11987654321"
        );

        // Act & Assert
        assertThrows(
            InvalidEmailException.class,
            () -> createCustomerUseCase.execute(command)
        );
    }

    @Test
    @DisplayName("Should throw InvalidNameException when invalid name is provided")
    void givenInvalidName_whenCreate_thenThrowInvalidNameException() {
        // Arrange
        CreateCustomerDto command = new CreateCustomerDto(
            "", // Nome inválido (vazio)
            "joao@example.com",
            "12345678901",
            "11987654321"
        );

        // Act & Assert
        assertThrows(
            InvalidNameException.class,
            () -> createCustomerUseCase.execute(command)
        );
    }

    @Test
    @DisplayName("Should throw InvalidPhoneException when invalid phone is provided")
    void givenInvalidPhone_whenCreate_thenThrowInvalidPhoneException() {
        // Arrange
        CreateCustomerDto command = new CreateCustomerDto(
            "João Silva",
            "joao@example.com",
            "12345678901",
            "123" // Telefone inválido (muito curto)
        );

        // Act & Assert
        assertThrows(
            InvalidPhoneException.class,
            () -> createCustomerUseCase.execute(command)
        );
    }
}

