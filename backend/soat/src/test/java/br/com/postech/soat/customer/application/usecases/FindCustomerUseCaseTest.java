package br.com.postech.soat.customer.application.usecases;

import br.com.postech.soat.commons.infrastructure.exception.NotFoundException;
import br.com.postech.soat.customer.application.dto.FindCustomerQuery;
import br.com.postech.soat.customer.domain.entity.Customer;
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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Tests for FindCustomerUseCase")
class FindCustomerUseCaseTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private FindCustomerUseCase findCustomerUseCase;

    @Test
    @DisplayName("Should return customer when CPF exists")
    void givenExistingCpf_whenFindByCpf_thenReturnCustomer() {
        // Arrange
        String cpf = "12345678901";
        FindCustomerQuery query = new FindCustomerQuery(cpf);

        when(customerRepository.findByCpf(cpf)).thenReturn(Optional.of(Customer.create(
            new br.com.postech.soat.customer.domain.valueobject.Name("João Silva"),
            new br.com.postech.soat.customer.domain.valueobject.Email("joao@example.com"),
            new br.com.postech.soat.customer.domain.valueobject.CPF(cpf),
            new br.com.postech.soat.customer.domain.valueobject.Phone("11987654321")
        )));

        // Act
        Customer result = findCustomerUseCase.execute(query);

        // Assert
        assertNotNull(result);
        assertEquals("João Silva", result.getName().value());
        assertEquals("joao@example.com", result.getEmail().value());
        assertEquals(cpf, result.getCpf().value());
        assertEquals("11987654321", result.getPhone().value());

        verify(customerRepository).findByCpf(cpf);
    }

    @Test
    @DisplayName("Should throw NotFoundException when CPF does not exist")
    void givenNonExistingCpf_whenFindByCpf_thenThrowNotFoundException() {
        // Arrange
        String cpf = "12345678901";
        FindCustomerQuery query = new FindCustomerQuery(cpf);

        when(customerRepository.findByCpf(cpf)).thenReturn(Optional.empty());

        // Act & Assert
        NotFoundException exception = assertThrows(
            NotFoundException.class,
            () -> findCustomerUseCase.execute(query)
        );

        assertEquals("Customer not found for the document identifier: " + cpf, exception.getMessage());
        verify(customerRepository).findByCpf(cpf);
    }
}