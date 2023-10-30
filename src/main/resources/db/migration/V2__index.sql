CREATE INDEX IF NOT EXISTS idx_busca_trgm ON pessoa USING gist (busca_trgm gist_trgm_ops);
CREATE INDEX IF NOT EXISTS idx_nome ON pessoa USING btree (nome);
