package com.manoelalmeidaio.rinhadebackendjavaspring.core.pessoa;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.UUID;

public class PessoaMapper {

  private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

  public static PessoaDto toDto(Pessoa domain) {
    PessoaDto dto = new PessoaDto();
    dto.setId(domain.getId());
    dto.setApelido(domain.getApelido());
    dto.setNome(domain.getNome());
    dto.setNascimento(LocalDate.parse(domain.getNascimento(), formatter));
    dto.setStack(domain.getStack() != null ? Arrays.asList(domain.getStack().split(",")) : null);

    return dto;
  }

  public static Pessoa toDomain(UUID uuid, PessoaDto dto) {
    Pessoa domain = new Pessoa();
    domain.setId(uuid);
    domain.setApelido(dto.getApelido());
    domain.setNome(dto.getNome());
    domain.setNascimento(dto.getNascimento().format(formatter));
    domain.setStack(dto.getStack() != null ? String.join(",", dto.getStack()) : null);

    return domain;
  }
}
