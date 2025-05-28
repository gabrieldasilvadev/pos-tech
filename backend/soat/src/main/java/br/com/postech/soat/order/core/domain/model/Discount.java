package br.com.postech.soat.order.core.domain.model;

import br.com.postech.soat.commons.domain.ValueObject;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Discount extends ValueObject {
    private final BigDecimal value;
}
