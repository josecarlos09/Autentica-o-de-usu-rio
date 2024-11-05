package br.com.produto.produto.DTO.login;

import br.com.produto.produto.model.loginUsuario.UsuarioRole;

public record RegistroDoUsuarioDTO(String login, String senha, UsuarioRole role) {
}
