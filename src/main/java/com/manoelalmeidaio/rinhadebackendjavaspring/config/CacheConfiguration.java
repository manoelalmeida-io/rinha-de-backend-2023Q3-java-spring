package com.manoelalmeidaio.rinhadebackendjavaspring.config;

import com.manoelalmeidaio.rinhadebackendjavaspring.domain.Pessoa;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
@EnableCaching
public class CacheConfiguration {

  @Bean
  public RedisTemplate<String, Pessoa> redisTemplate(RedisConnectionFactory connectionFactory) {
    RedisTemplate<String, Pessoa> template = new RedisTemplate<>();
    template.setConnectionFactory(connectionFactory);
    return template;
  }
}
