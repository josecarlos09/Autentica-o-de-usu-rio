package br.com.produto.produto.model.produto;
import br.com.produto.produto.DTO.produto.DadosAtualizacaoProduto;
import br.com.produto.produto.DTO.produto.DadosDeCadastroProduto;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.*;

@Entity(name = "produto") // Nome da tabela no banco
@Table(name = "produto") // Como vai ser chamada no spring
@EqualsAndHashCode(of = "id")// Indicaa a chave primary-key

// Construtores
@AllArgsConstructor // Construtor que inclua todos os atributos de uma classe no Spring
@NoArgsConstructor // Serve para criar um construtor padrão

// Métodos acessores e modificadores
@Getter
@Setter

public class Produto {
        @Id // Indica qual é o atributo chave primaria
        @GeneratedValue(strategy = GenerationType.SEQUENCE)// Gera números sequenciais
        @Column(name = "id")
        private Long id;
        @Column(name = "nome")
        private String nome;
        @Column(name = "preco")
        private Float preco;
        @Column(name = "ativo")
        private Boolean ativo = true;

    // Construtor para o uso do DTO DadosDeCadastroProduto
    public Produto(@Valid DadosDeCadastroProduto dadosDeCadastroProduto) {
        this.nome = dadosDeCadastroProduto.nome();
        this.preco = dadosDeCadastroProduto.preco();
    }

    public Boolean inativarProduto(){
       return this.ativo = false;
    }

    public Boolean ativarProduto() {
        return this.ativo = true;
    }

    public void atualizarInformacoes(DadosAtualizacaoProduto atualizarDados) {

    if (atualizarDados.nome()!=null){
        this.setNome(atualizarDados.nome());
    }
    if (atualizarDados.preco()!=null){
        this.setPreco(atualizarDados.preco());
    }

    }

}
