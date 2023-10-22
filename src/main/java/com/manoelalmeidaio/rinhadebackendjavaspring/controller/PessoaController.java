package com.manoelalmeidaio.rinhadebackendjavaspring.controller;

import com.manoelalmeidaio.rinhadebackendjavaspring.domain.Pessoa;
import com.manoelalmeidaio.rinhadebackendjavaspring.dto.PessoaDto;
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
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
public class PessoaController {

  private final PessoaRepository pessoaRepository;

  public PessoaController(PessoaRepository pessoaRepository) {
    this.pessoaRepository = pessoaRepository;
  }

  @PostMapping("/pessoas")
  public ResponseEntity<PessoaDto> cadastrar(@Valid @RequestBody PessoaDto dto) {

    if (this.pessoaRepository.existsByNome(dto.getNome())) {
      return ResponseEntity.unprocessableEntity().build();
    }

    Pessoa domain = new Pessoa();
    domain.setApelido(dto.getApelido());
    domain.setNome(dto.getNome());
    domain.setNascimento(dto.getNascimento());
    domain.setStack(dto.getStack() != null ? String.join(",", dto.getStack()) : null);

    this.pessoaRepository.save(domain);

    URI location = ServletUriComponentsBuilder
        .fromCurrentRequest()
        .path("/{id}")
        .buildAndExpand(domain.getId())
        .toUri();

    dto.setId(domain.getId());
    return ResponseEntity.created(location).body(dto);
  }

  @GetMapping("/pessoas/{id}")
  public ResponseEntity<PessoaDto> buscarPorId(@PathVariable String id) {
    Optional<PessoaDto> encontrada = this.pessoaRepository.findById(UUID.fromString(id))
        .map(domain -> {
          PessoaDto dto = new PessoaDto();
          dto.setId(domain.getId());
          dto.setApelido(domain.getApelido());
          dto.setNome(domain.getNome());
          dto.setNascimento(domain.getNascimento());
          dto.setStack(domain.getStack() != null ? Arrays.asList(domain.getStack().split(",")) : null);

          return dto;
        });

    return ResponseEntity.of(encontrada);
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
