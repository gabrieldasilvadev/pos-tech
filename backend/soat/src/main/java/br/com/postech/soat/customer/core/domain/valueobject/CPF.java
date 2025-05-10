package br.com.postech.soat.customer.core.domain.valueobject;

import br.com.postech.soat.customer.core.domain.exeption.InvalidCPFException;
import java.util.regex.Pattern;

public record CPF(String value) {

    private static final Pattern CPF_PATTERN = Pattern.compile("^\\d{11}$");

    public CPF {
        validate(value);
    }

    private void validate(String value) {
        if (value == null || !CPF_PATTERN.matcher(value).matches()) {
            throw new InvalidCPFException("CPF deve conter exatamente 11 dígitos numéricos");
        }
    }
}