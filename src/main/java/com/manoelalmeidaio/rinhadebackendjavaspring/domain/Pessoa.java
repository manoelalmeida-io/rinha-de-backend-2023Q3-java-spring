package com.manoelalmeidaio.rinhadebackendjavaspring.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.time.LocalDate;
import java.util.UUID;

@Entity
public class Pessoa {

  @Id
  private UUID id;
  private String apelido;
  private String nome;
  private LocalDate nascimento;
  private String stack;

  @Column(insertable = false, updatable = false)
  private String buscaTrgm;

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

  public String getStack() {
    return stack;
  }

  public void setStack(String stack) {
    this.stack = stack;
  }

  public String getBuscaTrgm() {
    return buscaTrgm;
  }

  public void setBuscaTrgm(String buscaTrgm) {
    this.buscaTrgm = buscaTrgm;
  }
}
