package br.edu.hashing.hashing.table;

import br.edu.hashing.domain.Registro;
import br.edu.hashing.hashing.functions.HashFunction;
import br.edu.hashing.hashing.metrics.Metrics;

import java.util.LinkedList;
import java.util.List;

/**
 * Implementação de Tabela-Hash com encadeamento externo.
 */
public class ChainingHashTable implements HashTable {
    private final List<Registro>[] table;
    private final int m;
    private final HashFunction func;
    private final Metrics metrics;

    @SuppressWarnings("unchecked")
    public ChainingHashTable(int tableSize, HashFunction func) {
        this.m = tableSize;
        this.table = (List<Registro>[]) new LinkedList[m];
        for (int i = 0; i < m; i++) {
            table[i] = new LinkedList<>();
        }
        this.func = func;
        this.metrics = new Metrics();
    }

    @Override
    public void insert(Registro reg) {
        int key = reg.getCodigoInt();
        int index = func.hash(key, m);
        List<Registro> bucket = table[index];
        if (!bucket.isEmpty()) {
            metrics.incInsertionCollisions();
        }
        bucket.add(0, reg);
    }

    @Override
    public boolean search(Registro reg) {
        int key = reg.getCodigoInt();
        int index = func.hash(key, m);
        List<Registro> bucket = table[index];
        for (Registro r : bucket) {
            metrics.incSearchComparisons();
            if (r.getCodigoInt() == key) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Metrics getMetrics() {
        return metrics;
    }

    @Override
    public int getTableSize() {
        return m;
    }

    @Override
    public void clear() {
        for (int i = 0; i < m; i++) {
            table[i].clear();
        }
        metrics.reset();
    }
}
