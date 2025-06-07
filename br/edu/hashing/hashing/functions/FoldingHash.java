package br.edu.hashing.hashing.functions;

/**
 * Função hash por dobramento (folding).
 */
public class FoldingHash implements HashFunction {
    @Override
    public int hash(int key, int tableSize) {
        int part1 = (key / 1_000_000) % 1000;
        int part2 = (key / 1000) % 1000;
        int part3 = key % 1000;
        int soma = part1 + part2 + part3;
        return soma % tableSize;
    }
}
