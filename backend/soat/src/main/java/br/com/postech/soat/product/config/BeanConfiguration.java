package br.com.postech.soat.product.config;

import br.com.postech.soat.product.core.ports.in.CreateProductUseCase;
import br.com.postech.soat.product.core.ports.out.ProductRepository;
import br.com.postech.soat.product.core.usecase.CreateProductUseCaseImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration("productBeanConfiguration")
public class BeanConfiguration {

    @Bean
    public CreateProductUseCase createProductUseCase (ProductRepository repository) {
        return new CreateProductUseCaseImpl(repository);
    }
}
