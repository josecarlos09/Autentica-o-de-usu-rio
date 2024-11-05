package br.com.produto.produto.repository.loginUsuario;

import br.com.produto.produto.model.loginUsuario.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    // O Spring Boot vai consultar pelo login (O atributo loging deve existir na entiade usuario)
    UserDetails findByLogin(String login);
}
