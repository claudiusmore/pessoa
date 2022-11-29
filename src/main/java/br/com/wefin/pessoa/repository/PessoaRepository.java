package br.com.wefin.pessoa.repository;

import br.com.wefin.pessoa.model.Pessoa;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PessoaRepository extends JpaRepository<Pessoa, Integer> {

    Page<Pessoa> findAll(Pageable pageable);
}
