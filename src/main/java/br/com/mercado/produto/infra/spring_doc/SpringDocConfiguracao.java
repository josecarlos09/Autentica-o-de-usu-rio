package br.com.produto.produto.infraestrutura.spring_doc;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocConfiguracao {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components()
                        .addSecuritySchemes("bearer-key", // O esquema de segurança sera bearer-key
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP) // O SecurityScheme estara no padrão HTTP
                                        .scheme("bearer")
                                        .bearerFormat("JWT"))) // Recebera um token JWT
                .info(new Info()
                        .title("Validando API") // Titulo da API
                        .description("API Rest implemeta uma validação de acesso com token JWT, e realiza um CRUD")
                        .contact(new Contact()
                                .name("Time Backend")
                                .email("backend@validacao.com"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("http://validacao/api/licenca")));
    }
}
