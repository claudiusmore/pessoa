package br.com.wefin.pessoa.service;

import br.com.wefin.pessoa.helper.PessoaHelper;
import br.com.wefin.pessoa.model.Pessoa;
import br.com.wefin.pessoa.repository.PessoaRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

/**
 * This class contains usage of Mockito
 */
@RunWith(MockitoJUnitRunner.class)
public class PessoaServiceTest {
    @Mock
    PessoaRepository repository;

    @InjectMocks
    PessoaService service;

    @Test
    public void listAllPessoa_test() {

        Pessoa pessoa1Ok = PessoaHelper.getPessoaOneOk();
        Pessoa pessoa2Ok = PessoaHelper.getPessoaTwoOk();
        Pessoa pessoa3Ok = PessoaHelper.getPessoaThreeOk();

        List<Pessoa> listOk = Arrays.asList(pessoa1Ok, pessoa2Ok, pessoa3Ok);

        Pageable pageable = PageRequest.of(0,3);
        Page<Pessoa> pageOk = new PageImpl<>(listOk, pageable, pageable.getPageSize());

        when(repository.findAll(pageable)).thenReturn(pageOk);

        Page<Pessoa> pageReturned = service.listAllPessoa(pageable);


        assertEquals(pageReturned.toList().get(0).getIdentificador(), "12345678912");
        assertEquals(pageReturned.toList().get(0).getTipoIdentificador(), "Cpf");

        assertEquals(pageReturned.toList().get(1).getIdentificador(), "69743667016123");
        assertEquals(pageReturned.toList().get(1).getTipoIdentificador(), "Cnpj");

        assertEquals(pageReturned.toList().get(2).getIdentificador(), "98765432112");
        assertEquals(pageReturned.toList().get(2).getTipoIdentificador(), "Cpf");

        assertEquals(pageReturned.getPageable().getPageNumber(), 0);
        assertEquals(pageReturned.getPageable().getPageSize(), 3);

        verify(repository, times(1)).findAll(pageable);
    }

    @Test
    public void getPessoa_test() {
        Pessoa pessoa = PessoaHelper.getPessoaOneOk();

        when(repository.findById(1)).thenReturn(Optional.of(pessoa));

        Pessoa pessoaRet = service.getPessoa(1);

        assertEquals(pessoaRet.getIdentificador(), "12345678912");
        assertEquals(pessoaRet.getTipoIdentificador(), "Cpf");

        verify(repository, times(1)).findById(1);
    }

    @Test
    public void savePessoa_test() {

        Pessoa pessoa = PessoaHelper.getPessoaOne();
        Pessoa pessoaOk = PessoaHelper.getPessoaOneOk();

        when(repository.save(any())).thenReturn(pessoaOk);
        Pessoa pessoaRet = service.savePessoa(pessoa);

        assertEquals(pessoaRet.getIdentificador(), "12345678912");
        assertEquals(pessoaRet.getTipoIdentificador(), "Cpf");

        verify(repository, times(1)).save(pessoa);
    }

    @Test
    public void deletePessoa_test() {
        service.deletePessoa(anyInt());
        verify(repository, times(1)).deleteById(anyInt());
    }

    @Test(expected = NoSuchElementException.class)
    public void getPessoaThenThrow_test() {
        when(repository.findById(any())).thenThrow(new NoSuchElementException());

        service.getPessoa(1);
        verify(repository, times(1)).findById(any());
    }

    @Test
    public void getCpfCnpj_test() {
        Pessoa pessoaFisica = PessoaHelper.getPessoaOne();
        Pessoa pessoaJuridica = PessoaHelper.getPessoaTwo();
        String cpf = service.getCpfCnpj(pessoaFisica.getIdentificador().length());
        String cnpj = service.getCpfCnpj(pessoaJuridica.getIdentificador().length());

        assertEquals(cpf, "Cpf");
        assertEquals(cnpj, "Cnpj");
    }
}
