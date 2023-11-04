package com.manoelalmeidaio.rinhadebackendjavaspring.core.pessoa;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.io.Serializable;
import java.util.UUID;

@Entity
public class Pessoa implements Serializable {

  @Id
  private UUID id;
  private String apelido;
  private String nome;
  private String nascimento;
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

  public String getNascimento() {
    return nascimento;
  }

  public void setNascimento(String nascimento) {
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
