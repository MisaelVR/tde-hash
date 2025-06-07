package br.edu.hashing.hashing.table;

import br.edu.hashing.domain.Registro;
import br.edu.hashing.hashing.functions.HashFunction;
import br.edu.hashing.hashing.metrics.Metrics;

/**
 * Implementação de Tabela-Hash com sondagem linear.
 */
public class OpenAddressingHashTable implements HashTable {
    private final Registro[] table;
    private final int m;
    private final HashFunction func;
    private final Metrics metrics;
    private int elementsCount = 0;

    public OpenAddressingHashTable(int tableSize, HashFunction func) {
        this.m = tableSize;
        this.table = new Registro[m];
        this.func = func;
        this.metrics = new Metrics();
    }

    @Override
    public void insert(Registro reg) {
        if (elementsCount >= m) return;
        int key = reg.getCodigoInt();
        int index = func.hash(key, m);
        while (table[index] != null) {
            metrics.incInsertionCollisions();
            index = (index + 1) % m;
        }
        table[index] = reg;
        elementsCount++;
    }

    @Override
    public boolean search(Registro reg) {
        int key = reg.getCodigoInt();
        int index = func.hash(key, m);

        while (table[index] != null) {
            metrics.incSearchComparisons();
            if (table[index].getCodigoInt() == key) {
                return true;
            }
            index = (index + 1) % m;
            metrics.incSearchComparisons();
            if (index == func.hash(key, m)) {
                return false;
            }
        }
        metrics.incSearchComparisons();
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
            table[i] = null;
        }
        elementsCount = 0;
        metrics.reset();
    }
}
