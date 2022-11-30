package br.com.wefin.pessoa.repository;

import br.com.wefin.pessoa.helper.PessoaHelper;
import br.com.wefin.pessoa.model.Pessoa;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class PessoaRepositoryTest {

    @Autowired
    PessoaRepository pessoaRepository;

    Pageable pageable;

    @Before
    public void Setup(){
        pageable = PageRequest.of(0,3);

        pessoaRepository.save(PessoaHelper.getPessoaOneOk());
        pessoaRepository.save(PessoaHelper.getPessoaTwoOk());
        pessoaRepository.save(PessoaHelper.getPessoaThreeOk());
    }

    @Test
    public void findOneTest(){
        Integer id = 1;
        Pessoa cli = pessoaRepository.findById(id).get();

        assertEquals(cli.getIdentificador(),"12345678912");
        assertEquals(cli.getTipoIdentificador(),"Cpf");
        assertEquals(cli.getId(), 1);
    }

    @Test
    public void findAllTest(){
        Page<Pessoa> page = pessoaRepository.findAll(pageable);

        assertEquals(page.toList().get(0).getIdentificador(), "12345678912");
        assertEquals(page.toList().get(1).getIdentificador(), "69743667016123");
        assertEquals(page.toList().get(2).getIdentificador(), "98765432112");
        assertEquals(page.toList().get(0).getTipoIdentificador(), "Cpf");
        assertEquals(page.toList().get(1).getTipoIdentificador(), "Cnpj");
        assertEquals(page.toList().get(2).getTipoIdentificador(), "Cpf");
        assertEquals(page.toList().get(0).getId(), 1);
        assertEquals(page.toList().get(1).getId(), 2);
        assertEquals(page.toList().get(2).getId(), 3);
        assertEquals(page.getContent().size(), 3);
        assertEquals(page.getTotalElements(), 3);
    }

    @Test
    public void updateTest(){
        Integer id = 1;

        Pessoa cli1 = pessoaRepository.findById(id).get();
        assertEquals(cli1.getNome(),"Claudio Moreira");

        cli1.setNome("Nome Alterado");

        pessoaRepository.save(cli1);
        Pessoa cli2 = pessoaRepository.findById(id).get();

        assertEquals(cli2.getId(), 1);
        assertEquals(cli2.getNome(),"Nome Alterado");
    }

    @Test
    public void deleteByIdTest(){
        Integer id = 2;

        Page<Pessoa> page1 = pessoaRepository.findAll(pageable);
        assertEquals(page1.toList().get(2).getId(), 3);
        assertEquals(page1.toList().size(), 3);

        pessoaRepository.deleteById(id);
        Page<Pessoa> page2 = pessoaRepository.findAll(pageable);
        assertEquals(page2.toList().get(1).getId(), 3);
        assertEquals(page2.toList().size(), 2);
    }
}