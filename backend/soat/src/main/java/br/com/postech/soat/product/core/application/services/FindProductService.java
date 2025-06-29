package br.com.postech.soat.product.core.application.services;

import br.com.postech.soat.commons.infrastructure.aop.monitorable.Monitorable;
import br.com.postech.soat.product.core.application.dto.FindProductRequest;
import br.com.postech.soat.product.core.domain.model.Product;
import br.com.postech.soat.product.core.ports.in.FindProductUseCase;
import br.com.postech.soat.product.core.ports.out.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
@RequiredArgsConstructor
@Monitorable
public class FindProductService implements FindProductUseCase {

    @Override
    public List<Product> findProduct(FindProductRequest request, ProductRepository productRepository) {
        return productRepository.findAll(request);
    }
}
