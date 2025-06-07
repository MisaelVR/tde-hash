package br.edu.hashing.hashing.functions;

/**
 * Interface para todas as funções-hash.
 */
public interface HashFunction {
    /**
     * @param key       valor inteiro (0..999_999_999)
     * @param tableSize tamanho da tabela (m)
     * @return índice no intervalo [0, m-1]
     */
    int hash(int key, int tableSize);
}
