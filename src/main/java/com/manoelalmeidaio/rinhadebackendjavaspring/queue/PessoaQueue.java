package com.manoelalmeidaio.rinhadebackendjavaspring.queue;

import com.manoelalmeidaio.rinhadebackendjavaspring.domain.Pessoa;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.util.List;

@Component
@EnableScheduling
public class PessoaQueue {

  private final JdbcTemplate jdbcTemplate;

  private final RedisTemplate<String, Pessoa> cache;

  public PessoaQueue(JdbcTemplate jdbcTemplate, RedisTemplate<String, Pessoa> cache) {
    this.jdbcTemplate = jdbcTemplate;
    this.cache = cache;
  }

  @Scheduled(fixedDelay = 1000L)
  public void insertAll() {

    Long tamanho = cache.opsForList().size("fila");

    if (tamanho == null) {
      return;
    }

    List<Pessoa> workQueue = cache.opsForList().leftPop("fila", tamanho);

    if (workQueue != null && !workQueue.isEmpty()) {
      var sql = "INSERT INTO pessoa (id, nome, apelido, nascimento, stack) VALUES (?, ?, ?, ?, ?)";

      jdbcTemplate.batchUpdate(sql, workQueue, workQueue.size(), (PreparedStatement ps, Pessoa pessoa) -> {
        ps.setObject(1, pessoa.getId());
        ps.setString(2, pessoa.getNome());
        ps.setString(3, pessoa.getApelido());
        ps.setString(4, pessoa.getNascimento());
        ps.setString(5, pessoa.getStack());
      });
    }
  }
}
