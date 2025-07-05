package br.com.postech.soat.customer.domain.model;

import br.com.postech.soat.customer.domain.valueobject.CPF;
import br.com.postech.soat.customer.domain.valueobject.Email;
import br.com.postech.soat.customer.domain.valueobject.Name;
import br.com.postech.soat.customer.domain.valueobject.Phone;
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
        assertEquals("João Silva", customer.getName().value());
        assertEquals("joao@mail.com", customer.getEmail().value());
        assertEquals("12345678901", customer.getCpf().value());
        assertEquals("11999999999", customer.getPhone().value());
    }

    @Test
    void givenCustomer_whenToString_thenReturnMaskedSensitiveData() {
        Customer customer = Customer.create(
            new Name("Maria"),
            new Email("maria@mail.com"),
            new CPF("12345678901"),
            new Phone("11999999999")
        );
        String toString = customer.toString();
        assertTrue(toString.contains("*****"));
        assertTrue(toString.contains("@"));
    }
}

