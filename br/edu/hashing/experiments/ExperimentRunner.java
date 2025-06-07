package br.edu.hashing.experiments;

import br.edu.hashing.data.DataGenerator;
import br.edu.hashing.domain.Registro;
import br.edu.hashing.hashing.functions.*;
import br.edu.hashing.hashing.table.ChainingHashTable;
import br.edu.hashing.hashing.table.HashTable;
import br.edu.hashing.hashing.table.OpenAddressingHashTable;
import br.edu.hashing.hashing.metrics.Metrics;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

/**
 * Classe responsável por executar todos os experimentos e exportar resultados em CSV.
 */
public class ExperimentRunner {

    public static void main(String[] args) {
        HashFunction[] hashFunctions = {
            new DivisionHash(),
            new MultiplicationHash(),
            new FoldingHash()
        };
        String[] hashNames = {"Division", "Multiplication", "Folding"};

        int[] tableSizes = {1000, 10000, 100000};
        int[] dataSizes = {1_000_000, 5_000_000, 20_000_000};
        long[] seeds = {12345L, 54321L, 99999L};
        String[] tableTypes = {"OpenAddressing", "Chaining"};

        String csvFile = "hash_experiments_results.csv";
        try (FileWriter writer = new FileWriter(csvFile)) {
            writer.write("TableType,TableSize,HashFunction,DataSize,InsertionTime(ns),InsertionCollisions,");
            for (int i = 1; i <= 5; i++) {
                writer.write("Search" + i + "Time(ns),Search" + i + "Comparisons,");
            }
            writer.write("AvgSearchTime(ns),AvgSearchComparisons\n");

            for (String tableType : tableTypes) {
                for (int ts : tableSizes) {
                    for (int hf = 0; hf < hashFunctions.length; hf++) {
                        HashFunction func = hashFunctions[hf];
                        String hashName = hashNames[hf];

                        for (int ds = 0; ds < dataSizes.length; ds++) {
                            int dataSize = dataSizes[ds];
                            long seed = seeds[ds];

                            Registro[] dataset = DataGenerator.generateDataset(dataSize, seed);

                            HashTable table;
                            if (tableType.equals("OpenAddressing")) {
                                table = new OpenAddressingHashTable(ts, func);
                            } else {
                                table = new ChainingHashTable(ts, func);
                            }

                            Metrics met = table.getMetrics();
                            met.reset();
                            long t0 = System.nanoTime();
                            for (Registro r : dataset) {
                                table.insert(r);
                            }
                            long t1 = System.nanoTime();
                            long insertionTime = t1 - t0;
                            long collisions = met.getInsertionCollisions();

                            int[] searchIndices = pickFiveRandomIndices(dataSize, seed + 111);
                            long totalSearchTime = 0;
                            long totalSearchComparisons = 0;

                            StringBuilder sbSearches = new StringBuilder();
                            for (int i = 0; i < 5; i++) {
                                met.addSearchComparisons(-met.getSearchComparisons());
                                int idx = searchIndices[i];
                                Registro toFind = dataset[idx];

                                long s0 = System.nanoTime();
                                boolean found = table.search(toFind);
                                long s1 = System.nanoTime();
                                long stime = s1 - s0;
                                long scomps = met.getSearchComparisons();

                                totalSearchTime += stime;
                                totalSearchComparisons += scomps;

                                sbSearches.append(stime).append(",").append(scomps).append(",");
                            }

                            double avgSearchTime = totalSearchTime / 5.0;
                            double avgSearchComparisons = totalSearchComparisons / 5.0;

                            writer.write(String.format(
                                "%s,%d,%s,%d,%d,%d,%s%.2f,%.2f\n",
                                tableType, ts, hashName, dataSize,
                                insertionTime, collisions,
                                sbSearches.toString(),
                                avgSearchTime, avgSearchComparisons
                            ));
                            writer.flush();

                            table.clear();
                        }
                    }
                }
            }

            System.out.println("Resultados exportados em: " + csvFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static int[] pickFiveRandomIndices(int dataSize, long seed) {
        Random rnd = new Random(seed);
        int[] idx = new int[5];
        for (int i = 0; i < 5; i++) {
            idx[i] = rnd.nextInt(dataSize);
        }
        return idx;
    }
}
