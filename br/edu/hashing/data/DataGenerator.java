package br.edu.hashing.data;

import br.edu.hashing.domain.Registro;

import java.util.Random;

/**
 * Gera vetores de objetos Registro com códigos aleatórios de 9 dígitos,
 * usando um seed fixo para garantir reprodutibilidade.
 */
public class DataGenerator {

    /**
     * Gera um array de N registros.
     *
     * @param n    quantidade de registros
     * @param seed seed do Random
     * @return array com N objetos Registro
     */
    public static Registro[] generateDataset(int n, long seed) {
        Registro[] dataset = new Registro[n];
        Random rnd = new Random(seed);
        for (int i = 0; i < n; i++) {
            int num = rnd.nextInt(1_000_000_000);
            String codigoStr = String.format("%09d", num);
            dataset[i] = new Registro(codigoStr);
        }
        return dataset;
    }
}
