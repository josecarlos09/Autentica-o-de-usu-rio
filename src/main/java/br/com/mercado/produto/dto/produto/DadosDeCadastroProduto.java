package br.com.produto.produto.DTO.produto;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DadosDeCadastroProduto (
        @NotNull
        Long id,
        @NotBlank
        @JsonAlias("Nome") String nome,
        @NotNull
        @JsonAlias("Preço") Float preco) {
}
