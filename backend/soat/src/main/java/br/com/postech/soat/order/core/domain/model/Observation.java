package br.com.postech.soat.order.core.domain.model;

import br.com.postech.soat.commons.domain.ValueObject;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Observation extends ValueObject {
    final private String text;
}
