package br.com.produto.produto.service.loginUsuario;

import br.com.produto.produto.DTO.login.RegistroDoUsuarioDTO;
import br.com.produto.produto.model.loginUsuario.Usuario;
import br.com.produto.produto.repository.loginUsuario.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class LoginService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    public ResponseEntity registrar(RegistroDoUsuarioDTO dados){
        /*
         * O método registrar, registrara um novo acesso de usuario.
         * O registro será realizado no modelo de acesso login e Senha.
         */
        if (this.usuarioRepository.findByLogin(dados.login()) != null){
            return ResponseEntity.badRequest().build();
        }

        String senha = new BCryptPasswordEncoder().encode(dados.senha());
        Usuario novoUsuario = new Usuario(dados.login(), senha, dados.role());

        this.usuarioRepository.save(novoUsuario);
        return ResponseEntity.ok().build();
    }

    public ResponseEntity excluirAcesso(Long id){
        this.usuarioRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
