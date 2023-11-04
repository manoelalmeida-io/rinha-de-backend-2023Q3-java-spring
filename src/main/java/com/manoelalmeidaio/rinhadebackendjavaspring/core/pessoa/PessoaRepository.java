package com.manoelalmeidaio.rinhadebackendjavaspring.core.pessoa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface PessoaRepository extends JpaRepository<Pessoa, UUID> {

  boolean existsByNome(String nome);

  @Query("select p from Pessoa p where p.buscaTrgm like :t")
  List<Pessoa> findByTermo(@Param("t") String t);
}
