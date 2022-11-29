package br.com.wefin.pessoa.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CNPJ;
import org.hibernate.validator.constraints.br.CPF;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

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
//    @CPF(groups = CpfGroup.class)
//    @CNPJ(groups = CnpjGroup.class)
//    @CpfOrCnpj(message = "o campo deve ser formatado como cpf ou cnpj")
    @Pattern(regexp = "([0-9]{11})|([0-9]{14})",message = "'identificador' deve ser cpf ou cnpj")
    private String identificador;

    private String tipoIdentificador;

}
