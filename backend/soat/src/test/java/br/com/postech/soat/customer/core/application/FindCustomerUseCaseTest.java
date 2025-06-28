package br.com.postech.soat.customer.core.application;

import br.com.postech.soat.commons.infrastructure.exception.NotFoundException;
import br.com.postech.soat.customer.core.application.dto.FindCustomerRequest;
import br.com.postech.soat.customer.core.application.services.FindCustomerService;
import br.com.postech.soat.customer.core.domain.model.Customer;
import br.com.postech.soat.customer.core.domain.valueobject.CPF;
import br.com.postech.soat.customer.core.domain.valueobject.Email;
import br.com.postech.soat.customer.core.domain.valueobject.Name;
import br.com.postech.soat.customer.core.domain.valueobject.Phone;
import br.com.postech.soat.customer.core.ports.in.FindCustomerUseCase;
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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Tests for FindCustomerUseCase")
class FindCustomerUseCaseTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private FindCustomerService findCustomerUseCase;

    @Test
    @DisplayName("Should return customer when CPF exists")
    void givenExistingCpf_whenFindByCpf_thenReturnCustomer() {
        // Arrange
        String cpf = "12345678901";
        FindCustomerRequest request = new FindCustomerRequest(cpf);

        Customer expectedCustomer = Customer.create(
            new Name("João Silva"),
            new Email("joao@example.com"),
            new CPF(cpf),
            new Phone("11987654321")
        );

        when(customerRepository.findByCpf(cpf)).thenReturn(Optional.of(expectedCustomer));

        // Act
        Customer result = findCustomerUseCase.execute(request);

        // Assert
        assertNotNull(result);
        assertEquals(expectedCustomer.getId().value(), result.getId().value());
        assertEquals(expectedCustomer.getName().value(), result.getName().value());
        assertEquals(expectedCustomer.getEmail().value(), result.getEmail().value());
        assertEquals(expectedCustomer.getCpf().value(), result.getCpf().value());
        assertEquals(expectedCustomer.getPhone().value(), result.getPhone().value());

        verify(customerRepository).findByCpf(cpf);
    }

    @Test
    @DisplayName("Should throw NotFoundException when CPF does not exist")
    void givenNonExistingCpf_whenFindByCpf_thenThrowNotFoundException() {
        // Arrange
        String cpf = "12345678901";
        FindCustomerRequest request = new FindCustomerRequest(cpf);

        when(customerRepository.findByCpf(cpf)).thenReturn(Optional.empty());

        // Act & Assert
        NotFoundException exception = assertThrows(
            NotFoundException.class,
            () -> findCustomerUseCase.execute(request)
        );

        assertEquals("Customer not found for the document identifier: " + cpf, exception.getMessage());
        verify(customerRepository).findByCpf(cpf);
    }
}