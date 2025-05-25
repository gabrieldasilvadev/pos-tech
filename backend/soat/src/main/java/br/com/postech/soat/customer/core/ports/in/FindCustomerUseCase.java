package br.com.postech.soat.customer.core.ports.in;

import br.com.postech.soat.customer.core.application.dto.FindCustomerQuery;
import br.com.postech.soat.customer.core.domain.model.Customer;

public interface FindCustomerUseCase {

    Customer findByCpf(FindCustomerQuery query);

}
