package br.com.produto.produto.infraestrutura.seguranca;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity // Indeica que eu vou fazer as configurações de segurança
public class SegurancaConfiguracao {

    @Autowired
    SecurityFilter securityFilter;

    // Corrente de filtro de segurança (Estou configurando novamente)
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // A politica de seção será STATELESS

                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(HttpMethod.POST, "/autorizacao/logar").permitAll() // Permite disparar a permição de login
                        .requestMatchers(HttpMethod.POST, "/autorizacao/registrar").permitAll()
                        .requestMatchers(HttpMethod.POST, "/autorizacao/excluir-acesso/{id}").permitAll()

                        .requestMatchers(HttpMethod.POST, "/produto/cadastrar").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/produto/consultar").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/produto/excluir/").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/produto/consulta-detalhada/{id}").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/produto/atualizar").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/produto/inativar/{id}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/produto/ativar/{id}").hasRole("ADMIN")

                        .anyRequest().authenticated()
                )
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)// faz um filtro de verificação antes do UsernamePasswordAuthenticationFilter.class
                .build();
    }

   @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

   @Bean
   // Retorna uma senha criptografada
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Classe para fazer a criptografia (algoritmo de RACH)
    }
}
