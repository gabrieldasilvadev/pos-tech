package br.com.postech.soat.customer.config;

import br.com.postech.soat.customer.core.application.CreateCustomerService;
import br.com.postech.soat.customer.core.application.FindCustomerService;
import br.com.postech.soat.customer.core.ports.in.CreateCustomerUseCase;
import br.com.postech.soat.customer.core.ports.in.FindCustomerUseCase;
import br.com.postech.soat.customer.core.ports.out.CustomerRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CustomerConfig {

    @Bean
    public FindCustomerUseCase findCustomerUseCase(CustomerRepository customerRepository) {
        return new FindCustomerService(customerRepository);
    }

    @Bean
    public CreateCustomerUseCase createCustomerUseCase(CustomerRepository customerRepository) {
        return new CreateCustomerService(customerRepository);
    }
}