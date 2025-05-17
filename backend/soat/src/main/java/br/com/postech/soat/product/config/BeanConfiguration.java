package br.com.postech.soat.product.config;

import br.com.postech.soat.product.core.ports.in.IProductUseCase;
import br.com.postech.soat.product.core.ports.out.IProductRepository;
import br.com.postech.soat.product.core.usecase.ProductUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration("productBeanConfiguration")
public class BeanConfiguration {

    @Bean
    public IProductUseCase createProductUseCase (IProductRepository repository) {
        return new ProductUseCase(repository);
    }
}
