package br.com.wefin.pessoa.helper;

import br.com.wefin.pessoa.model.Pessoa;

public class PessoaHelper {

    public static Pessoa getPessoaOne(){
        Pessoa pessoa = new Pessoa();

        pessoa.setIdentificador("12345678912");
        pessoa.setNome("Claudio Moreira");
        return pessoa;
    }

    public static Pessoa getPessoaTwo(){
        Pessoa pessoa = new Pessoa();

        pessoa.setIdentificador("69743667016123");
        pessoa.setNome("Empresa Ltda");
        return pessoa;
    }

    public static Pessoa getPessoaThree(){
        Pessoa pessoa = new Pessoa();

        pessoa.setIdentificador("1234567891");
        pessoa.setNome("Pessoa Cpf Errado");
        return pessoa;
    }

    public static Pessoa getPessoaOneOk(){
        Pessoa pessoa = new Pessoa();

        pessoa.setIdentificador("12345678912");
        pessoa.setNome("Claudio Moreira");
        pessoa.setTipoIdentificador("Cpf");
        return pessoa;
    }

    public static Pessoa getPessoaTwoOk(){
        Pessoa pessoa = new Pessoa();

        pessoa.setIdentificador("69743667016123");
        pessoa.setNome("Empresa Ltda");
        pessoa.setTipoIdentificador("Cnpj");
        return pessoa;
    }

    public static Pessoa getPessoaThreeOk(){
        Pessoa pessoa = new Pessoa();

        pessoa.setIdentificador("98765432112");
        pessoa.setNome("Pessoa Dois");
        pessoa.setTipoIdentificador("Cpf");
        return pessoa;
    }

    public static Pessoa getPessoaFour(){
        Pessoa pessoa = new Pessoa();

        pessoa.setIdentificador("1234567891234");
        pessoa.setNome("Empresa Identificador Errado");
        return pessoa;
    }
}
