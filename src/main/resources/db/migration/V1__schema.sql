CREATE TABLE IF NOT EXISTS pessoa (
   id uuid PRIMARY KEY,
   nome VARCHAR(100) NOT NULL,
   apelido VARCHAR(32) NOT NULL,
   nascimento VARCHAR(10),
   stack VARCHAR(255)
);
