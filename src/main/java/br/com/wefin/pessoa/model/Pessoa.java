package br.com.wefin.pessoa.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "pessoa")
public class Pessoa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "nome")
    @NotBlank(message = "'nome' é obrigatório")
    private String nome;

    @NotBlank(message = "'identificador' é obrigatório")
    @Pattern(regexp = "(/d{11})|(/d{14})",message = "'identificador' deve ter tamanho 11(cpf) ou 14(cnpj)")
    private String identificador;

    private String tipoIdentificador;

}
