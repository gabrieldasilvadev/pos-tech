package br.com.postech.soat.product.adapters.out;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface ProductJpaRepository extends JpaRepository<ProductEntity, UUID> {
}
