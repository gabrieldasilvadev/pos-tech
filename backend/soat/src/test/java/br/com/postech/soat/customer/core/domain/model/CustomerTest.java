package br.com.postech.soat.customer.core.domain.model;

import br.com.postech.soat.customer.core.domain.valueobject.CPF;
import br.com.postech.soat.customer.core.domain.valueobject.Email;
import br.com.postech.soat.customer.core.domain.valueobject.Name;
import br.com.postech.soat.customer.core.domain.valueobject.Phone;
import org.junit.jupiter.api.Test;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;

class CustomerTest {
    @Test
    void givenValidNameEmailCpfPhone_whenCreateCustomer_thenReturnCustomerWithCorrectFields() {
        Name name = new Name("João Silva");
        Email email = new Email("joao@mail.com");
        CPF cpf = new CPF("12345678901");
        Phone phone = new Phone("11999999999");
        Customer customer = Customer.create(name, email, cpf, phone);
        assertNotNull(customer.getId());
        assertEquals("João Silva", customer.getName());
        assertEquals("joao@mail.com", customer.getEmail());
        assertEquals("12345678901", customer.getCpf());
        assertEquals("11999999999", customer.getPhone());
    }

    @Test
    void givenCustomer_whenToString_thenReturnMaskedSensitiveData() {
        Customer customer = Customer.builder()
            .id(UUID.randomUUID())
            .name("Maria")
            .cpf("12345678901")
            .email("maria@mail.com")
            .phone("11999999999")
            .build();
        String toString = customer.toString();
        assertTrue(toString.contains("*****"));
        assertTrue(toString.contains("@"));
    }
}

