package br.com.postech.soat.product.config;

import br.com.postech.soat.product.core.ports.in.CreateProductUseCase;
import br.com.postech.soat.product.core.ports.in.DeleteProductUseCase;
import br.com.postech.soat.product.core.ports.in.UpdateProductUseCase;
import br.com.postech.soat.product.core.ports.out.ProductRepository;
import br.com.postech.soat.product.core.usecase.CreateProductUseCaseImpl;
import br.com.postech.soat.product.core.usecase.DeleteProductUseCaseImpl;
import br.com.postech.soat.product.core.usecase.UpdateProductUseCaseImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration("productBeanConfiguration")
public class BeanConfiguration {

    @Bean
    public CreateProductUseCase createProductUseCase (ProductRepository repository) {
        return new CreateProductUseCaseImpl(repository);
    }

    @Bean
    public UpdateProductUseCase updateProductUseCase(ProductRepository repository) {
        return new UpdateProductUseCaseImpl(repository);
    }

    @Bean
    public DeleteProductUseCase deleteProductUseCase(ProductRepository repository) {
        return new DeleteProductUseCaseImpl(repository);
    }
}
