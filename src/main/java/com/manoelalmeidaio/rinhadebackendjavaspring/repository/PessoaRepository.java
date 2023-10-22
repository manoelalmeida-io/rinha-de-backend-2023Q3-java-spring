package com.manoelalmeidaio.rinhadebackendjavaspring.repository;

import com.manoelalmeidaio.rinhadebackendjavaspring.domain.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PessoaRepository extends JpaRepository<Pessoa, UUID> {

  boolean existsByNome(String nome);
}
