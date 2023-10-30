package com.manoelalmeidaio.rinhadebackendjavaspring.queue;

import com.manoelalmeidaio.rinhadebackendjavaspring.domain.Pessoa;
import com.manoelalmeidaio.rinhadebackendjavaspring.dto.PessoaDto;
import com.manoelalmeidaio.rinhadebackendjavaspring.mapper.PessoaMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

public class PessoaCommand {

  private final Pessoa pessoa;
  private final DeferredResult<ResponseEntity<PessoaDto>> deferredResult;
  private final PessoaDto dto;
  private final URI location;

  public PessoaCommand(Pessoa pessoa, URI location, DeferredResult<ResponseEntity<PessoaDto>> deferredResult) {
    this.pessoa = pessoa;
    this.deferredResult = deferredResult;
    this.location = location;
    this.dto = PessoaMapper.toDto(pessoa);
  }

  public Pessoa getPessoa() {
    return pessoa;
  }

  public DeferredResult<ResponseEntity<PessoaDto>> getDeferredResult() {
    return deferredResult;
  }

  public PessoaDto getDto() {
    return dto;
  }

  public URI getLocation() {
    return location;
  }
}
