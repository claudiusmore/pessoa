package br.com.wefin.pessoa.service;

import br.com.wefin.pessoa.model.Pessoa;
import br.com.wefin.pessoa.repository.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.Optional;

@Service
public class PessoaService {

    @Autowired
    private PessoaRepository pessoaRepository;


    public Page<Pessoa> listAllPessoa(Pageable page) {
        Page<Pessoa> response = pessoaRepository.findAll(page);
        return response;
    }

    public Pessoa getPessoa(Integer id) {
        Pessoa pessoa = pessoaRepository.findById(id).get();
        return pessoa;
    }

    public Pessoa savePessoa(Pessoa pessoa) {
        pessoa.setTipoIdentificador(null);
        return pessoaRepository.save(pessoa);
    }

    public void deletePessoa(Integer id) {
        pessoaRepository.deleteById(id);
    }

}
