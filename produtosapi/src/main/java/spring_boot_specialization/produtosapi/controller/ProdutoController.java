package spring_boot_specialization.produtosapi.controller;

import org.springframework.web.bind.annotation.*;
import spring_boot_specialization.produtosapi.model.Produto;
import spring_boot_specialization.produtosapi.repository.ProdutoRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/*
@RestController marca a classe como um controlador REST. Ele combina:
@Controller (registra a classe como controlador MVC) +
@ResponseBody (retorno dos métodos e vai para o corpo da resposta — normalmente JSON)
 */
@RestController
/*
@RequestMapping: Anotação do Spring MVC para mapear rotas HTTP.
Define rota específica e condições (método HTTP, consumes, produces, params, headers etc.).
Usar em  mapeamento CRUD (@GetMapping, @PostMapping, @PutMapping, @DeleteMapping, @PatchMapping).
 */
@RequestMapping("/produtos")
public class ProdutoController {

    private ProdutoRepository produtoRepository;

    public ProdutoController(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    @PostMapping("/salvar")
    public Produto salvar(@RequestBody Produto produto){
        System.out.println("Produto recebido: " + produto);
        var id = UUID.randomUUID().toString();
        produto.setId(id);
        produtoRepository.save(produto);
        return produto;
    }

    @GetMapping("/obter/{id}")
    public Produto obterPorId(@PathVariable  String id) {
        return produtoRepository.findById(id).orElse(null);
    }

    @DeleteMapping("/apagar/{id}")
    public String apagarPorId(@PathVariable String id) {
        produtoRepository.deleteById(id);
        return String.format("Produto %s apagado.", id);
    }

    @PutMapping("/editar/{id}")
    public Produto editarPorId(@PathVariable String id, @RequestBody Produto produto){
        Optional<Produto> byId = produtoRepository.findById(id);
        Produto produtoN = byId.orElseGet(Produto::new);
        produtoN.setNome(produto.getNome());
        produtoN.setDescricao(produto.getDescricao());
        produtoN.setPreco(produto.getPreco());
        return produtoRepository.save(produtoN);
    }

    @GetMapping("/busca-por-nome")
    public List<Produto> obterProdutosPorNome(@RequestParam("nome") String nome){
        return produtoRepository.findByNome(nome);
    }
}
