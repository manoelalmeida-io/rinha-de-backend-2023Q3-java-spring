package com.manoelalmeidaio.rinhadebackendjavaspring.controller;

import com.manoelalmeidaio.rinhadebackendjavaspring.domain.Pessoa;
import com.manoelalmeidaio.rinhadebackendjavaspring.dto.PessoaDto;
import com.manoelalmeidaio.rinhadebackendjavaspring.mapper.PessoaMapper;
import com.manoelalmeidaio.rinhadebackendjavaspring.repository.PessoaRepository;
import jakarta.validation.Valid;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@RestController
public class PessoaController {

  private final PessoaRepository pessoaRepository;
  private final RedisTemplate<String, Pessoa> cache;

  public PessoaController(PessoaRepository pessoaRepository, RedisTemplate<String, Pessoa> cache) {
    this.pessoaRepository = pessoaRepository;
    this.cache = cache;
  }

  @PostMapping("/pessoas")
  public ResponseEntity<PessoaDto> cadastrar(@Valid @RequestBody PessoaDto dto) {
    UUID id = UUID.randomUUID();

    URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(id).toUri();

    Pessoa domain = PessoaMapper.toDomain(id, dto);

    var existeCache = cache.hasKey(domain.getNome());

    if (Boolean.TRUE.equals(existeCache) || this.pessoaRepository.existsByNome(domain.getNome())) {
      return ResponseEntity.unprocessableEntity().build();
    }

    cache.opsForValue().set(domain.getId().toString(), domain);
    cache.opsForValue().set(domain.getNome(), domain);
    cache.opsForList().rightPush("fila", domain);

    dto.setId(id);
    return ResponseEntity.created(location).body(dto);
  }

  @GetMapping("/pessoas/{id}")
  public ResponseEntity<PessoaDto> buscarPorId(@PathVariable String id) {
    var pessoaCache = this.cache.opsForValue().get(id);

    if (pessoaCache != null) {
      return ResponseEntity.ok(PessoaMapper.toDto(pessoaCache));
    }

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
