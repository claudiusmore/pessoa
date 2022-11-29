package br.com.wefin.pessoa.service;

import br.com.wefin.pessoa.model.Pessoa;
import br.com.wefin.pessoa.repository.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PessoaService {

    @Autowired
    private PessoaRepository pessoaRepository;


    public Page<Pessoa> listAllPessoa(Pageable page) {
        return pessoaRepository.findAll(page);
    }

    public Pessoa getPessoa(Integer id) {
        return pessoaRepository.findById(id).get();
    }

    public Pessoa savePessoa(Pessoa pessoa) {
        pessoa.setTipoIdentificador(getCpfCnpj(pessoa.getIdentificador().length()));
        return pessoaRepository.save(pessoa);
    }

    public void deletePessoa(Integer id) {
        pessoaRepository.deleteById(id);
    }

    private String getCpfCnpj(int identificador) {
        return identificador == 11 ? "Cpf" : "Cnpj";
    }

}
