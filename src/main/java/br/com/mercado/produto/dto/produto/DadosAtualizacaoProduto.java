package br.com.produto.produto.DTO.produto;

import com.fasterxml.jackson.annotation.JsonAlias;

public record DadosAtualizacaoProduto(
        Long id,
        @JsonAlias("Nome") String nome,
        @JsonAlias("Preço") Float preco,
        @JsonAlias("Existe")Boolean ativo
) {
}
