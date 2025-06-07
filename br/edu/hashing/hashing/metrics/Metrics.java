package br.edu.hashing.hashing.metrics;

/**
 * Mantém métricas de um experimento de tabela-hash.
 */
public class Metrics {
    private long insertionCollisions = 0;
    private long searchComparisons = 0;

    public void reset() {
        insertionCollisions = 0;
        searchComparisons = 0;
    }

    public void incInsertionCollisions() {
        insertionCollisions++;
    }

    public long getInsertionCollisions() {
        return insertionCollisions;
    }

    public void incSearchComparisons() {
        searchComparisons++;
    }

    public void addSearchComparisons(long n) {
        searchComparisons += n;
    }

    public long getSearchComparisons() {
        return searchComparisons;
    }
}
