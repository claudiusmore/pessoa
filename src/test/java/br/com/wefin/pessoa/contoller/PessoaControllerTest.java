package br.com.wefin.pessoa.contoller;

import br.com.wefin.pessoa.helper.PessoaHelper;
import br.com.wefin.pessoa.model.Pessoa;
import br.com.wefin.pessoa.service.PessoaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class PessoaControllerTest {
    private static final String BASE_URL = "/pessoas";
    private static final String PESSOA_ID = "/pessoas/1";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    PessoaService service;

    @BeforeEach
    public void Setup(){
    }

    @Test
    public void listAllPessoaTest() throws Exception {

        Pessoa pessoa1 = PessoaHelper.getPessoaOneOk();
        Pessoa pessoa2 = PessoaHelper.getPessoaTwoOk();
        Pessoa pessoa3 = PessoaHelper.getPessoaThreeOk();

        List<Pessoa> list = Arrays.asList(pessoa1, pessoa2, pessoa3);
        Pageable pageable = PageRequest.of(1,3);
        Page<Pessoa> page = new PageImpl<>(list, pageable, pageable.getPageSize());

        when(service.listAllPessoa(any())).thenReturn(page);

        mockMvc.perform(get(BASE_URL))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].identificador", is("12345678912")))
                .andExpect(jsonPath("$.content[0].tipoIdentificador", is("Cpf")))
                .andExpect(jsonPath("$.content[1].identificador", is("69743667016123")))
                .andExpect(jsonPath("$.content[1].tipoIdentificador", is("Cnpj")))
                .andExpect(jsonPath("$.content[2].identificador", is("98765432112")))
                .andExpect(jsonPath("$.content[2].tipoIdentificador", is("Cpf")));

        verify(service, times(1)).listAllPessoa(any());
    }

    @Test
    public void getPessoaTest() throws Exception {

        Pessoa pessoa = PessoaHelper.getPessoaOneOk();

        when(service.getPessoa(any())).thenReturn(pessoa);

        mockMvc.perform(get(PESSOA_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.identificador", is("12345678912")))
                .andExpect(jsonPath("$.tipoIdentificador", is("Cpf")));

        verify(service, times(1)).getPessoa(any());
    }

    @Test
    public void insertPessoaTest() throws Exception {

        Pessoa pessoa = PessoaHelper.getPessoaOne();
        Pessoa pessoaOk = PessoaHelper.getPessoaOneOk();

        when(service.savePessoa(any())).thenReturn(pessoaOk);

        mockMvc.perform(post(BASE_URL)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(pessoa)))
                .andExpect(jsonPath("$.identificador", is("12345678912")))
                .andExpect(jsonPath("$.tipoIdentificador", is("Cpf")))
                .andExpect(status().isCreated());

        verify(service, times(1)).savePessoa(any());
    }

    @Test
    public void updatePessoaTest() throws Exception {

        Pessoa pessoa = PessoaHelper.getPessoaOne();
        Pessoa pessoaOk = PessoaHelper.getPessoaOneOk();

        when(service.getPessoa(any())).thenReturn(pessoa);
        when(service.savePessoa(any())).thenReturn(pessoaOk);

        mockMvc.perform(put(PESSOA_ID)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(pessoa)))
                .andExpect(jsonPath("$.identificador", is("12345678912")))
                .andExpect(jsonPath("$.tipoIdentificador", is("Cpf")))
                .andExpect(status().isOk());

        verify(service, times(1)).getPessoa(any());
        verify(service, times(1)).savePessoa(any());
    }

    @Test
    public void deletePessoaTest() throws Exception {
        Pessoa pessoa = PessoaHelper.getPessoaOneOk();

        when(service.getPessoa(any())).thenReturn(pessoa);

        mockMvc.perform(delete(PESSOA_ID)
                        .contentType("application/json"))
                .andExpect(status().isNoContent());

        verify(service, times(1)).getPessoa(any());
        verify(service, times(1)).deletePessoa(any());
    }


    @Test
    public void listAllPessoaReturnNotFoundTest() throws Exception {

        when(service.listAllPessoa(any())).thenThrow(NoSuchElementException.class);

        mockMvc.perform(get(BASE_URL))
                .andExpect(status().isInternalServerError());

        verify(service, times(1)).listAllPessoa(any());
    }

    @Test
    public void getPessoaReturnNotFoundTest() throws Exception {

        when(service.getPessoa(any())).thenThrow(NoSuchElementException.class);

        mockMvc.perform(get(PESSOA_ID))
                .andExpect(status().isNotFound());

        verify(service, times(1)).getPessoa(any());
    }


    @Test
    public void insertPessoaReturnCpfCnpjErrorTest() throws Exception {

        Pessoa pessoaNok = PessoaHelper.getPessoaFour();

        mockMvc.perform(post(BASE_URL)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(pessoaNok)))
                .andExpect(jsonPath("$.identificador", is("'identificador' deve ter tamanho 11(cpf) ou 14(cnpj)")))
                .andExpect(status().isBadRequest());

        verify(service, times(0)).getPessoa(any());
        verify(service, times(0)).savePessoa(any());
    }

    @Test
    public void updatePessoaReturnNotFoundTest() throws Exception {

        Pessoa pessoa = PessoaHelper.getPessoaOne();

        when(service.getPessoa(any())).thenThrow(NoSuchElementException.class);

        mockMvc.perform(put(PESSOA_ID)
                .contentType("application/json")
                        .content(objectMapper.writeValueAsString(pessoa)))
                .andExpect(status().isNotFound());

        verify(service, times(1)).getPessoa(any());
        verify(service, times(0)).savePessoa(any());
    }

    @Test
    public void deletePessoaReturnNotFoundTest() throws Exception {

        when(service.getPessoa(any())).thenThrow(NoSuchElementException.class);

        mockMvc.perform(delete(PESSOA_ID)
                        .contentType("application/json"))
                .andExpect(status().isNotFound());

        verify(service, times(1)).getPessoa(any());
        verify(service, times(0)).deletePessoa(any());
    }
}
