package br.com.postech.soat.customer.core.application;

import br.com.postech.soat.commons.infrastructure.exception.ResourceConflictException;
import br.com.postech.soat.customer.core.application.dto.CreateCustomerCommand;
import br.com.postech.soat.customer.core.domain.exception.InvalidCpfException;
import br.com.postech.soat.customer.core.domain.exception.InvalidEmailException;
import br.com.postech.soat.customer.core.domain.exception.InvalidNameException;
import br.com.postech.soat.customer.core.domain.exception.InvalidPhoneException;
import br.com.postech.soat.customer.core.domain.model.Customer;
import br.com.postech.soat.customer.core.ports.out.CustomerRepository;
import java.util.UUID;
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
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Tests for CreateCustomerService")
class CreateCustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CreateCustomerService createCustomerService;

    @Test
    @DisplayName("Should create a customer successfully when valid data is provided")
    void givenValidCommand_whenCreate_thenReturnCustomer() {
        // Arrange
        CreateCustomerCommand command = new CreateCustomerCommand(
            "João Silva",
            "joao@example.com",
            "12345678901",
            "11987654321"
        );

        Customer expectedCustomer = Customer.builder()
            .id(UUID.randomUUID())
            .name("João Silva")
            .email("joao@example.com")
            .cpf("12345678901")
            .phone("11987654321")
            .build();

        when(customerRepository.existsByCpf("12345678901")).thenReturn(false);
        when(customerRepository.existsByEmail("joao@example.com")).thenReturn(false);
        when(customerRepository.existsByPhone("11987654321")).thenReturn(false);
        when(customerRepository.save(any(Customer.class))).thenReturn(expectedCustomer);

        // Act
        Customer result = createCustomerService.create(command);

        // Assert
        assertNotNull(result);
        assertEquals(expectedCustomer.getName(), result.getName());
        assertEquals(expectedCustomer.getEmail(), result.getEmail());
        assertEquals(expectedCustomer.getCpf(), result.getCpf());
        assertEquals(expectedCustomer.getPhone(), result.getPhone());

        verify(customerRepository).existsByCpf("12345678901");
        verify(customerRepository).existsByEmail("joao@example.com");
        verify(customerRepository).existsByPhone("11987654321");
        verify(customerRepository).save(any(Customer.class));
    }

    @Test
    @DisplayName("Should throw ResourceConflictException when CPF already exists")
    void givenExistingCpf_whenCreate_thenThrowResourceConflictException() {
        // Arrange
        CreateCustomerCommand command = new CreateCustomerCommand(
            "João Silva",
            "joao@example.com",
            "12345678901",
            "11987654321"
        );

        Customer existingCustomer = Customer.builder()
            .id(UUID.randomUUID())
            .name("Cliente Existente")
            .email("existente@example.com")
            .cpf("12345678901")
            .phone("11999999999")
            .build();

        when(customerRepository.existsByCpf("12345678901")).thenReturn(true);
        when(customerRepository.existsByEmail("joao@example.com")).thenReturn(false);
        when(customerRepository.existsByPhone("11987654321")).thenReturn(false);

        // Act & Assert
        ResourceConflictException exception = assertThrows(
            ResourceConflictException.class,
            () -> createCustomerService.create(command)
        );

        assertEquals("Customer registration failed. Conflicts found: CPF: 12345678901",
            exception.getMessage());
        verify(customerRepository).existsByCpf("12345678901");
        verify(customerRepository).existsByEmail("joao@example.com");
        verify(customerRepository).existsByPhone("11987654321");
        verify(customerRepository, never()).save(any(Customer.class));
    }

    @Test
    @DisplayName("Should throw ResourceConflictException when email already exists")
    void givenExistingEmail_whenCreate_thenThrowResourceConflictException() {
        // Arrange
        CreateCustomerCommand command = new CreateCustomerCommand(
            "João Silva",
            "joao@example.com",
            "12345678901",
            "11987654321"
        );

        when(customerRepository.existsByCpf("12345678901")).thenReturn(false);
        when(customerRepository.existsByEmail("joao@example.com")).thenReturn(true);
        when(customerRepository.existsByPhone("11987654321")).thenReturn(false);

        // Act & Assert
        ResourceConflictException exception = assertThrows(
            ResourceConflictException.class,
            () -> createCustomerService.create(command)
        );

        assertEquals("Customer registration failed. Conflicts found: Email: joao@example.com",
            exception.getMessage());
        verify(customerRepository).existsByCpf("12345678901");
        verify(customerRepository).existsByEmail("joao@example.com");
        verify(customerRepository).existsByPhone("11987654321");
        verify(customerRepository, never()).save(any(Customer.class));
    }

    @Test
    @DisplayName("Should throw ResourceConflictException when phone already exists")
    void givenExistingPhone_whenCreate_thenThrowResourceConflictException() {
        // Arrange
        CreateCustomerCommand command = new CreateCustomerCommand(
            "João Silva",
            "joao@example.com",
            "12345678901",
            "11987654321"
        );

        when(customerRepository.existsByCpf("12345678901")).thenReturn(false);
        when(customerRepository.existsByEmail("joao@example.com")).thenReturn(false);
        when(customerRepository.existsByPhone("11987654321")).thenReturn(true);

        // Act & Assert
        ResourceConflictException exception = assertThrows(
            ResourceConflictException.class,
            () -> createCustomerService.create(command)
        );

        assertEquals("Customer registration failed. Conflicts found: Phone: 11987654321",
            exception.getMessage());
        verify(customerRepository).existsByCpf("12345678901");
        verify(customerRepository).existsByEmail("joao@example.com");
        verify(customerRepository).existsByPhone("11987654321");
        verify(customerRepository, never()).save(any(Customer.class));
    }

    @Test
    @DisplayName("Should throw ResourceConflictException when multiple fields already exists")
    void givenMultipleExistingFields_whenCreate_thenThrowResourceConflictException() {
        // Arrange
        CreateCustomerCommand command = new CreateCustomerCommand(
            "João Silva",
            "joao@example.com",
            "12345678901",
            "11987654321"
        );

        when(customerRepository.existsByCpf("12345678901")).thenReturn(true);
        when(customerRepository.existsByEmail("joao@example.com")).thenReturn(true);
        when(customerRepository.existsByPhone("11987654321")).thenReturn(true);

        // Act & Assert
        ResourceConflictException exception = assertThrows(
            ResourceConflictException.class,
            () -> createCustomerService.create(command)
        );

        assertEquals("Customer registration failed. Conflicts found: CPF: 12345678901, " +
            "Email: joao@example.com, Phone: 11987654321", exception.getMessage());
        verify(customerRepository).existsByCpf("12345678901");
        verify(customerRepository).existsByEmail("joao@example.com");
        verify(customerRepository).existsByPhone("11987654321");
        verify(customerRepository, never()).save(any(Customer.class));
    }

    @Test
    @DisplayName("Should throw InvalidCpfException when invalid CPF is provided")
    void givenInvalidCpf_whenCreate_thenThrowInvalidCpfException() {
        // Arrange
        CreateCustomerCommand command = new CreateCustomerCommand(
            "João Silva",
            "joao@example.com",
            "123", // Invalid CPF (very short)
            "11987654321"
        );

        // Act & Assert
        assertThrows(
            InvalidCpfException.class,
            () -> createCustomerService.create(command)
        );
    }

    @Test
    @DisplayName("Should throw InvalidEmailException when invalid email is provided")
    void givenInvalidEmail_whenCreate_thenThrowInvalidEmailException() {
        // Arrange
        CreateCustomerCommand command = new CreateCustomerCommand(
            "João Silva",
            "email-invalido", // Invalid Email
            "12345678901",
            "11987654321"
        );

        // Act & Assert
        assertThrows(
            InvalidEmailException.class,
            () -> createCustomerService.create(command)
        );
    }

    @Test
    @DisplayName("Should throw InvalidNameException when invalid name is provided")
    void givenInvalidName_whenCreate_thenThrowInvalidNameException() {
        // Arrange
        CreateCustomerCommand command = new CreateCustomerCommand(
            "", // Invalid name (empty)
            "joao@example.com",
            "12345678901",
            "11987654321"
        );

        // Act & Assert
        assertThrows(
            InvalidNameException.class,
            () -> createCustomerService.create(command)
        );
    }

    @Test
    @DisplayName("Should throw InvalidPhoneException when invalid phone is provided")
    void givenInvalidPhone_whenCreate_thenThrowInvalidPhoneException() {
        // Arrange
        CreateCustomerCommand command = new CreateCustomerCommand(
            "João Silva",
            "joao@example.com",
            "12345678901",
            "123" // Invalid phone (very short)
        );

        // Act & Assert
        assertThrows(
            InvalidPhoneException.class,
            () -> createCustomerService.create(command)
        );
    }
}

