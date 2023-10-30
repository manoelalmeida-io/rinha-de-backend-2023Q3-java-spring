package com.manoelalmeidaio.rinhadebackendjavaspring.mapper;

import com.manoelalmeidaio.rinhadebackendjavaspring.domain.Pessoa;
import com.manoelalmeidaio.rinhadebackendjavaspring.dto.PessoaDto;

import java.util.Arrays;

public class PessoaMapper {

  public static PessoaDto toDto(Pessoa domain) {
    PessoaDto dto = new PessoaDto();
    dto.setId(domain.getId());
    dto.setApelido(domain.getApelido());
    dto.setNome(domain.getNome());
    dto.setNascimento(domain.getNascimento());
    dto.setStack(domain.getStack() != null ? Arrays.asList(domain.getStack().split(",")) : null);

    return dto;
  }
}
