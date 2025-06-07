package br.edu.hashing.hashing.functions;

/**
 * Função hash por resto da divisão: h(k) = k % m.
 */
public class DivisionHash implements HashFunction {
    @Override
    public int hash(int key, int tableSize) {
        return key % tableSize;
    }
}
