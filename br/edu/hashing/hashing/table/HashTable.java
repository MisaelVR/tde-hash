package br.edu.hashing.hashing.table;

import br.edu.hashing.domain.Registro;
import br.edu.hashing.hashing.metrics.Metrics;

/**
 * Interface genérica para Tabela-Hash.
 */
public interface HashTable {
    void insert(Registro reg);
    boolean search(Registro reg);
    Metrics getMetrics();
    int getTableSize();
    void clear();
}
