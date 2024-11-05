package br.com.produto.produto.controler;

import br.com.produto.produto.DTO.produto.DadosAtualizacaoProduto;
import br.com.produto.produto.DTO.produto.DadosDeCadastroProduto;
import br.com.produto.produto.DTO.produto.DadosDetalhadosProduto;
import br.com.produto.produto.DTO.produto.DadosListagemProdutos;
import br.com.produto.produto.repository.produto.ProdutoRepository;
import br.com.produto.produto.service.produto.ProdutoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("produto")
public class ProdutoControler {
    @Autowired
    private ProdutoService produtoService;
    @Autowired
    private ProdutoRepository produtoRepository;

    @PostMapping("/cadastrar") // Indica que será uma inserção de dados
    @Transactional // Indica que está acontecendo uma trasação ativa no BD
    public ResponseEntity<DadosDetalhadosProduto> cadastrar(@RequestBody DadosDeCadastroProduto dados, UriComponentsBuilder uriComponentsBuilder){
        return produtoService.cadastrarProduto(dados, uriComponentsBuilder);
    }

    @GetMapping("/consultar")
    public ResponseEntity<Page<DadosListagemProdutos>>consultar(Pageable paginacao){
        return produtoService.listagemProduto(paginacao);
    }

    @GetMapping("/consulta-detalhada/{id}")
    public ResponseEntity consultaDetalhada(@PathVariable Long id){
        return produtoService.consultaDetalhada(id);
    }

    @DeleteMapping("excluir/{id}")
    @Transactional
    public void exclusaoFisica(@PathVariable Long id){
        this.produtoService.apagarProduto(id);
    }

    @PutMapping("/atualizar")
    @Transactional
    public ResponseEntity atualizar(@RequestBody @Valid DadosAtualizacaoProduto atualizarDados){
        return produtoService.modificar(atualizarDados);
    }

    @DeleteMapping("/inativar/{id}")
    @Transactional
    public ResponseEntity inativarProduto(@PathVariable Long id){
        return produtoService.inativarProduto(id);
    }

    @PutMapping("/ativar/{id}")
    @Transactional
    public ResponseEntity ativarProduto(@PathVariable Long id){
        return produtoService.ativarProduto(id);
    }
}
