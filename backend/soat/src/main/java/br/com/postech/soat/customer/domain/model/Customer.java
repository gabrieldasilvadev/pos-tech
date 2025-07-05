package br.com.postech.soat.customer.domain.model;

import br.com.postech.soat.customer.domain.util.MaskUtil;
import br.com.postech.soat.customer.domain.valueobject.CPF;
import br.com.postech.soat.customer.domain.valueobject.CustomerId;
import br.com.postech.soat.customer.domain.valueobject.Email;
import br.com.postech.soat.customer.domain.valueobject.Name;
import br.com.postech.soat.customer.domain.valueobject.Phone;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Customer {

    private final CustomerId id;
    private final Name name;
    private final CPF cpf;
    private final Email email;
    private final Phone phone;

    public static Customer create(Name name, Email email, CPF cpf, Phone phone) {
        return new Customer(
            CustomerId.generate(),
            name,
            cpf,
            email,
            phone
        );
    }

    public static Customer reconstitute(CustomerId id, Name name, CPF cpf, Email email, Phone phone) {
        return new Customer(id, name, cpf, email, phone);
    }

    @Override
    public String toString() {
        return "Customer{" +
            "id=" + id.value() +
            ", cpf='" + MaskUtil.maskCpf(cpf.value()) + '\'' +
            ", name='" + name.value() + '\'' +
            ", email='" + MaskUtil.maskEmail(email.value()) + '\'' +
            ", phone='" + MaskUtil.maskPhone(phone.value()) + '\'' +
            '}';
    }
}
