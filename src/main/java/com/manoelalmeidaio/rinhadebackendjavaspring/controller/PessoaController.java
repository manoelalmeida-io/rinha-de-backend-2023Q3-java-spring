package com.manoelalmeidaio.rinhadebackendjavaspring.controller;

import com.manoelalmeidaio.rinhadebackendjavaspring.domain.Pessoa;
import com.manoelalmeidaio.rinhadebackendjavaspring.dto.PessoaDto;
import com.manoelalmeidaio.rinhadebackendjavaspring.mapper.PessoaMapper;
import com.manoelalmeidaio.rinhadebackendjavaspring.queue.PessoaQueue;
import com.manoelalmeidaio.rinhadebackendjavaspring.repository.PessoaRepository;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

@RestController
public class PessoaController {

  private final PessoaQueue pessoaQueue;
  private final PessoaRepository pessoaRepository;
  private final Executor executor = Executors.newFixedThreadPool(100);

  public PessoaController(PessoaRepository pessoaRepository, PessoaQueue pessoaQueue) {
    this.pessoaRepository = pessoaRepository;
    this.pessoaQueue = pessoaQueue;
  }

  @PostMapping("/pessoas")
  public DeferredResult<ResponseEntity<PessoaDto>> cadastrar(@Valid @RequestBody PessoaDto dto) {

    DeferredResult<ResponseEntity<PessoaDto>> deferredResult = new DeferredResult<>(10000L);

    UUID id = UUID.randomUUID();

    URI location = ServletUriComponentsBuilder
        .fromCurrentRequest().path("/{id}").buildAndExpand(id).toUri();

    CompletableFuture.runAsync(() -> {
      if (this.pessoaRepository.existsByNome(dto.getNome())) {
        deferredResult.setResult(ResponseEntity.unprocessableEntity().build());
        return;
      }

      Pessoa domain = new Pessoa();
      domain.setId(id);
      domain.setApelido(dto.getApelido());
      domain.setNome(dto.getNome());
      domain.setNascimento(dto.getNascimento());
      domain.setStack(dto.getStack() != null ? String.join(",", dto.getStack()) : null);

      pessoaQueue.add(domain, location, deferredResult);
    }, executor);

    return deferredResult;
  }

  @GetMapping("/pessoas/{id}")
  public ResponseEntity<PessoaDto> buscarPorId(@PathVariable String id) {
    Optional<PessoaDto> encontrada = this.pessoaRepository.findById(UUID.fromString(id)).map(PessoaMapper::toDto);
    return ResponseEntity.of(encontrada);
  }

  @GetMapping("/pessoas")
  public ResponseEntity<List<PessoaDto>> buscarPorTermo(@RequestParam(required = false) String t) {
    if (Objects.isNull(t)) {
      return ResponseEntity.badRequest().build();
    }

    return ResponseEntity.ok(this.pessoaRepository.findByTermo(t).parallelStream().map(PessoaMapper::toDto).toList());
  }

  @GetMapping("/contagem-pessoas")
  public String contagem() {
    return "" + this.pessoaRepository.count();
  }
}
