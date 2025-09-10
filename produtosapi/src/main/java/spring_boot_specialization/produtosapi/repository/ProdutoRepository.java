package spring_boot_specialization.produtosapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spring_boot_specialization.produtosapi.model.Produto;

import java.util.List;

public interface ProdutoRepository extends JpaRepository<Produto, String> {

    List<Produto> findByNome(String nome);
}
