package br.com.wefin.pessoa;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "API de Pessoas", version = "1.0", description = "Informações de pessoas contendo validação de CPF ou CNPJ"))
public class PessoaApplication {

    public static void main(String[] args) {
        SpringApplication.run(PessoaApplication.class, args);
    }

}