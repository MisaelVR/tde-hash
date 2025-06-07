package br.edu.hashing.hashing.functions;

/**
 * Função hash por multiplicação (Knuth):
 * h(k) = floor(m * frac(k * A)),
 * onde A = (sqrt(5)-1)/2.
 */
public class MultiplicationHash implements HashFunction {
    private static final double A = (Math.sqrt(5) - 1) / 2;

    @Override
    public int hash(int key, int tableSize) {
        double product = key * A;
        double fracPart = product - Math.floor(product);
        return (int) Math.floor(tableSize * fracPart);
    }
}
