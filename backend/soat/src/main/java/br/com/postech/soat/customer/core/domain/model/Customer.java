package br.com.postech.soat.customer.core.domain.model;

import br.com.postech.soat.customer.core.domain.util.MaskUtil;
import br.com.postech.soat.customer.core.domain.valueobject.CPF;
import br.com.postech.soat.customer.core.domain.valueobject.CustomerId;
import br.com.postech.soat.customer.core.domain.valueobject.Email;
import br.com.postech.soat.customer.core.domain.valueobject.Name;
import br.com.postech.soat.customer.core.domain.valueobject.Phone;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class Customer {

    private final UUID id;
    private final String name;
    private final String cpf;
    private final String email;
    private final String phone;

    public static Customer create(Name name, Email email, CPF cpf, Phone phone) {
        return new Customer(
            CustomerId.generate().value(),
            name.value(),
            cpf.value(),
            email.value(),
            phone.value()
        );
    }

    @Override
    public String toString() {
        return "Customer{" +
            "id=" + id +
            ", cpf='" + MaskUtil.maskCpf(cpf) + '\'' +
            ", name='" + name + '\'' +
            ", email='" + MaskUtil.maskEmail(email) + '\'' +
            ", phone='" + MaskUtil.maskPhone(phone) + '\'' +
            '}';
    }
}
