CREATE TABLE IF NOT EXISTS pessoa (
    id uuid PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    apelido VARCHAR(32) NOT NULL,
    nascimento DATE,
    stack VARCHAR(255),
    busca_trgm TEXT GENERATED ALWAYS AS (LOWER(nome || apelido || stack)) STORED
);

CREATE EXTENSION IF NOT EXISTS PG_TRGM;
