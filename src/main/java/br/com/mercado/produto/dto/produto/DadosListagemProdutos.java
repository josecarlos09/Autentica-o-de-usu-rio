package br.com.produto.produto.DTO.produto;

import br.com.produto.produto.model.produto.Produto;

public record DadosListagemProdutos (Long id, String nome, Float preco, Boolean ativo){
        public DadosListagemProdutos(Produto produto){
            this(produto.getId(), produto.getNome(), produto.getPreco(), produto.getAtivo());
        }
}
