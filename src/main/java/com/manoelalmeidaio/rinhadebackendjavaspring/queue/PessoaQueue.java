package com.manoelalmeidaio.rinhadebackendjavaspring.queue;

import com.manoelalmeidaio.rinhadebackendjavaspring.domain.Pessoa;
import com.manoelalmeidaio.rinhadebackendjavaspring.dto.PessoaDto;
import com.manoelalmeidaio.rinhadebackendjavaspring.repository.PessoaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.async.DeferredResult;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@Component
@EnableScheduling
public class PessoaQueue {

  private final PessoaRepository pessoaRepository;

  private List<PessoaCommand> pessoas;

  public PessoaQueue(PessoaRepository pessoaRepository) {
    this.pessoaRepository = pessoaRepository;
    this.pessoas = new ArrayList<>();
  }

  public synchronized void add(Pessoa pessoa, URI location, DeferredResult<ResponseEntity<PessoaDto>> deferredResult) {
    this.pessoas.add(new PessoaCommand(pessoa, location, deferredResult));
  }

  @Scheduled(fixedRate = 1000L)
  public void insertAll() {

    if (!this.pessoas.isEmpty()) {
      List<PessoaCommand> workPool = List.copyOf(this.pessoas);
      this.pessoas.removeAll(workPool);

      List<Pessoa> pessoasInsert = workPool.stream().map(PessoaCommand::getPessoa).toList();

      this.pessoaRepository.saveAll(pessoasInsert);

      workPool.parallelStream().forEach(command ->
          command.getDeferredResult().setResult(ResponseEntity.created(command.getLocation()).body(command.getDto())));
    }
  }
}
