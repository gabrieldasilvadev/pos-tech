package br.com.postech.soat.customer.adapters.out.persistente;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerJpaRepository extends JpaRepository<CustomerEntity, UUID> {


    @Query("SELECT c FROM CustomerEntity c WHERE :cpf IS NULL OR c.cpf = :cpf")
    List<CustomerEntity> find(String cpf);

    boolean existsByCpf(String cpf);
}