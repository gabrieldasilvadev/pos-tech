package br.com.postech.soat.customer.core.application;

import br.com.postech.soat.commons.infrastructure.exception.NotFoundException;
import br.com.postech.soat.customer.core.domain.model.Customer;
import br.com.postech.soat.customer.core.ports.in.FindCustomerUseCase.FindCustomerQuery;
import br.com.postech.soat.customer.core.ports.out.CustomerRepository;
import java.util.Optional;
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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Tests for FindCustomerService")
class FindCustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private FindCustomerService findCustomerService;

    @Test
    @DisplayName("Should return customer when CPF exists")
    void givenExistingCpf_whenFindByCpf_thenReturnCustomer() {
        // Arrange
        String cpf = "12345678901";
        FindCustomerQuery query = new FindCustomerQuery(cpf);

        Customer expectedCustomer = Customer.builder()
            .id(UUID.randomUUID())
            .name("João Silva")
            .email("joao@example.com")
            .cpf(cpf)
            .phone("11987654321")
            .build();

        when(customerRepository.findByCpf(cpf)).thenReturn(Optional.of(expectedCustomer));

        // Act
        Customer result = findCustomerService.findByCpf(query);

        // Assert
        assertNotNull(result);
        assertEquals(expectedCustomer.getId(), result.getId());
        assertEquals(expectedCustomer.getName(), result.getName());
        assertEquals(expectedCustomer.getEmail(), result.getEmail());
        assertEquals(expectedCustomer.getCpf(), result.getCpf());
        assertEquals(expectedCustomer.getPhone(), result.getPhone());

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
            () -> findCustomerService.findByCpf(query)
        );

        assertEquals("Cliente não encontrado para o CPF: " + cpf, exception.getMessage());
        verify(customerRepository).findByCpf(cpf);
    }
}