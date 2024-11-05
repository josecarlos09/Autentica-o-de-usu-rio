package br.com.produto.produto.controler;

import br.com.produto.produto.DTO.login.AutenticacaoDTO;
import br.com.produto.produto.DTO.login.LoginRespostaDTO;
import br.com.produto.produto.DTO.login.RegistroDoUsuarioDTO;
import br.com.produto.produto.infraestrutura.seguranca.TokenService;
import br.com.produto.produto.model.loginUsuario.Usuario;
import br.com.produto.produto.service.loginUsuario.LoginService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("autorizacao")
public class AutenticacaoControler{

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @Autowired
    LoginService loginService;

    @PostMapping("/logar")
    public ResponseEntity logar(@RequestBody @Valid AutenticacaoDTO dados){
        var usuarioSenha = new UsernamePasswordAuthenticationToken(dados.login(), dados.senha()); // Receber o login e senha passada de um DTO
        var autenticacao = this.authenticationManager.authenticate(usuarioSenha);

        var token = tokenService.gerarToken((Usuario) autenticacao.getPrincipal());
        return ResponseEntity.ok(new LoginRespostaDTO(token)); // Estara sendo retornado um token via DTO
    }

    @PostMapping("/registrar")
    public ResponseEntity registrar(@RequestBody @Valid RegistroDoUsuarioDTO dados){
        return loginService.registrar(dados);
    }

    @DeleteMapping("/excluir-acesso/{login}")
    @Transactional
    public void exclusaoFisica(@PathVariable Long login){
        this.loginService.excluirAcesso(login);
    }
}
