package br.com.produto.produto.service.produto;

import br.com.produto.produto.DTO.produto.DadosAtualizacaoProduto;
import br.com.produto.produto.DTO.produto.DadosDeCadastroProduto;
import br.com.produto.produto.DTO.produto.DadosDetalhadosProduto;
import br.com.produto.produto.DTO.produto.DadosListagemProdutos;
import br.com.produto.produto.model.produto.Produto;
import br.com.produto.produto.repository.produto.ProdutoRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    // Cadastrar Produtos
    public ResponseEntity<DadosDetalhadosProduto>cadastrarProduto(@RequestBody @Valid DadosDeCadastroProduto dadosDeCadastroProduto, UriComponentsBuilder uriComponentsBuilder) {

        var produto = new Produto(dadosDeCadastroProduto);
        produtoRepository.save(produto);

        // Gerando cabeçalho
        // Como é um cadatastro, o tipo do retorno será código 201
        var uri = uriComponentsBuilder.path("/produto/{id}").buildAndExpand(produto.getId()).toUri();
        return ResponseEntity.created(uri).body(new DadosDetalhadosProduto(produto));
    }

    //Paginação de produtos
    public ResponseEntity<Page<DadosListagemProdutos>>listagemProduto(Pageable paginacao){
        // Recebe um paginação e transforma em um map
        var page = produtoRepository.findAllByAtivoTrue(paginacao).map(DadosListagemProdutos:: new);
        return ResponseEntity.ok(page); // Retornara no corpo da requisição
    }

    // Consulta detalhada
    public ResponseEntity consultaDetalhada(@PathVariable Long id){
        /* O método consultaDetalhada recebe um DTO que será passado para o usuario que fizer a requisição
         * Por que não usar o mesmo DTO do cadastro? Não utilizei o mesmo DTO do cadastro, porque terá informações cadastradas que não será informado ao usuario.
         */
        var produto = produtoRepository.getReferenceById(id);
        return ResponseEntity.ok(new DadosDetalhadosProduto(produto));
    }

    //Atualizar dados de produto
    public ResponseEntity modificar(@RequestBody @Valid DadosAtualizacaoProduto atualizarDados){
        /* As informações que serão alteradas é especificada pelo DTO DadosAtualizacaoMedico
         * Para indentificar o registro que será atualizado será usado a chave primaria da tabela, que é o id.
         */
        var produto = produtoRepository.getReferenceById(atualizarDados.id());
        produto.atualizarInformacoes(atualizarDados);

        return ResponseEntity.ok(new DadosDetalhadosProduto(produto));
    }

    // o método inativarProduto realiza a inativação do cadastro desse produto que está ativo no estoque
    public ResponseEntity inativarProduto(@PathVariable Long numero_id){
        var produto = produtoRepository.getReferenceById(numero_id);//Fara referência a tabela que possui o id informado
        produto.inativarProduto();

        return ResponseEntity.noContent().build(); // Indica requisição processada e corpo vazio
    }

    // o método ativarProduto reativa a ativação do produto no cadastro de estoque
    public ResponseEntity ativarProduto(@PathVariable Long numero_id){
        var produto = produtoRepository.getReferenceById(numero_id);//Fara referência a tabela que possui o id informado
        produto.ativarProduto();

        return ResponseEntity.noContent().build(); // Indica requisição processada e corpo vazio
    }

    // Excluir produto
    public void apagarProduto(Long id) {
        this.produtoRepository.deleteById(id);
    }
}
