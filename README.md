# Projeto - Rinha de Backend 2023Q3 (Spring)

Backend feito em Java com Spring Boot para atender aos requisitos da
[Rinha de Backend 2023 Q3](https://github.com/zanfranceschi/rinha-de-backend-2023-q3)

> ⚠️ Esse projeto foi criado após o resultado da rinha e disponibilização do stress-test com o intuito 
> de servir como estudo de técnicas de melhoria de desempenho em aplicações web com Spring

## Ferramentas

### Java
* Versão 17
* JDK Oracle GraalVM versão 17.0.9

### Spring Framework 6
* GraalVM Native Support
* Spring Web (MVC)
* Spring Data JPA
* Spring Cache
* Flyway Migrations

### PostgreSQL 
* Generated columns
* Indices Btree e Gist

### Redis
* Cache compartilhado

### NGIX
* Balancemento de carga

## Implementação

As APIs precisavam expor os seguintes endpoints:

* `POST /pessoas` – para criar um recurso pessoa.
* `GET /pessoas/[:id]` – para consultar um recurso criado com a requisição anterior.
* `GET /pessoas?t=[:termo da busca]` – para fazer uma busca por pessoas.
* `GET /contagem-pessoas` – endpoint especial para contagem de pessoas cadastradas.

---

### `POST`  `/pessoas`

Nesse endpoint foi utilizado o cacheamento para evitar que muitos inserts fossem realizados de uma 
vez no banco de dados. Então, primeiro inserimos os objetos de `pessoa` no cache e depois
através de uma rotina persistimos o cache no banco de dados com batch inserts.

Saiba mais: \
https://redisson.org/glossary/write-through-and-write-behind-caching.html \
https://www.baeldung.com/spring-jdbc-batch-inserts

---

### `GET` `/pessoas?t=[:termo da busca]`

Nesse endpoint para melhorar o desempenho da busca os campos utilizados na busca foram agrupados em
outra coluna que possui um índice gist baseado em [trigramas](https://en.wikipedia.org/wiki/Trigram) 
para realizar buscas mais rápidas por similaridade.

Os trigramas são conjuntos de 3 letras que podem ser utilizados para verificar a similaridade entre
frases e palavras com objetivo de realizar [pesquisas fuzzy](https://www.freecodecamp.org/news/fuzzy-string-matching-with-postgresql/)
no banco de dados.

Saiba mais: \
https://www.postgresql.org/docs/current/pgtrgm.html#PGTRGM-INDEX

## Rodando o Projeto

Monte a imagem da aplicação spring nativa:
```bash
gradle bootBuildImage
```

Após isso podemos iniciar todos os containers com:
```bash
docker compose up
```

## Referências:

https://docs.spring.io/spring-boot/docs/current/reference/html/native-image.html \
https://docs.spring.io/spring-framework/reference/core/aot.html \
https://developer.redis.com/develop/java/redis-and-spring-course/lesson_2/ \
https://www.baeldung.com/spring-jdbc-batch-inserts \
https://redisson.org/glossary/write-through-and-write-behind-caching.html \
https://www.postgresql.org/docs/current/pgtrgm.html

