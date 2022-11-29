package br.com.wefin.pessoa.controller;

import br.com.wefin.pessoa.model.Pessoa;
import br.com.wefin.pessoa.service.PessoaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/pessoas")
public class PessoaController {

    @Autowired
    PessoaService pessoaService;

    // GET
    @GetMapping("")
    public ResponseEntity<Page<Pessoa>> list(@PageableDefault(size = 3) Pageable pageable) {
        try {
            Page<Pessoa> pessoas = pessoaService.listAllPessoa(pageable);
            return new ResponseEntity<>(pessoas, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // GET (id)
    @GetMapping("/{id}")
    public ResponseEntity<Pessoa> get(@PathVariable Integer id) {
        try {
            Pessoa pessoa = pessoaService.getPessoa(id);
            return new ResponseEntity<>(pessoa, HttpStatus.OK); // 200
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // 404
        }
    }

    // POST
    @PostMapping("")
    public ResponseEntity<Pessoa> add(@Valid @RequestBody Pessoa pessoa) {
        Pessoa pes = pessoaService.savePessoa(pessoa);
        return new ResponseEntity<>(pes, HttpStatus.CREATED); // 201
    }

    // PUT
    @PutMapping("/{id}")
    public ResponseEntity<Pessoa> update(@PathVariable Integer id, @Valid @RequestBody Pessoa pessoa) {
        try {
            Pessoa atual = pessoaService.getPessoa(id);
            pessoa.setId(atual.getId());
            Pessoa pes = pessoaService.savePessoa(pessoa);
            return new ResponseEntity<>(pes, HttpStatus.OK); // 200
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // 404
        }
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        try {
            Pessoa pessoa = pessoaService.getPessoa(id);
            pessoaService.deletePessoa(pessoa.getId());
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // 204
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // 404
        }
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> erros = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((erro) -> {
            String campo = ((FieldError) erro).getField();
            String msgErro = erro.getDefaultMessage();
            erros.put(campo, msgErro);
        });
        return erros;
    }
}
