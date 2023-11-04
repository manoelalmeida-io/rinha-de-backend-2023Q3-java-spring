package com.manoelalmeidaio.rinhadebackendjavaspring.core.pessoa;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class PessoaDto {

  private UUID id;

  @NotNull
  @Length(max = 32)
  private String apelido;

  @NotNull
  @Length(max = 100)
  private String nome;

  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private LocalDate nascimento;

  private List<@Valid @Length(max = 32) String> stack;

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public String getApelido() {
    return apelido;
  }

  public void setApelido(String apelido) {
    this.apelido = apelido;
  }

  public String getNome() {
    return nome;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  public LocalDate getNascimento() {
    return nascimento;
  }

  public void setNascimento(LocalDate nascimento) {
    this.nascimento = nascimento;
  }

  public List<String> getStack() {
    return stack;
  }

  public void setStack(List<String> stack) {
    this.stack = stack;
  }
}
