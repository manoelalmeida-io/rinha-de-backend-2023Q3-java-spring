package com.manoelalmeidaio.rinhadebackendjavaspring.controller;

import com.manoelalmeidaio.rinhadebackendjavaspring.domain.Pessoa;
import com.manoelalmeidaio.rinhadebackendjavaspring.repository.PessoaRepository;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
public class PessoaController {

  private final PessoaRepository pessoaRepository;

  public PessoaController(PessoaRepository pessoaRepository) {
    this.pessoaRepository = pessoaRepository;
  }

  @PostMapping("/pessoas")
  public ResponseEntity<Pessoa> cadastrar(@Valid @RequestBody Pessoa pessoa) {

    if (this.pessoaRepository.existsByNome(pessoa.getNome())) {
      return ResponseEntity.unprocessableEntity().build();
    }

    this.pessoaRepository.save(pessoa);

    URI location = ServletUriComponentsBuilder
        .fromCurrentRequest()
        .path("/{id}")
        .buildAndExpand(pessoa.getId())
        .toUri();

    return ResponseEntity.created(location).body(pessoa);
  }

  @GetMapping("/pessoas/{id}")
  public ResponseEntity<Pessoa> buscarPorId(@PathVariable String uuid) {
    return ResponseEntity.of(this.pessoaRepository.findById(UUID.fromString(uuid)));
  }

  @GetMapping("/pessoas")
  public ResponseEntity<List<Pessoa>> buscarPorTermo(@RequestParam String t) {
    return ResponseEntity.ok(new ArrayList<>());
  }

  @GetMapping("/contagem-pessoas")
  public String contagem() {
    return "" + this.pessoaRepository.count();
  }
}
