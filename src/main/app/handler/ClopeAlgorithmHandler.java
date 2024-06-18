package main.app.handler;

import main.app.models.Clope;
import main.app.models.Cluster;
import main.app.models.Transaction;

import main.errors.ErrorMessage;

import main.io.reader.FileReader;
import main.io.writer.ConsoleWriter;

import java.io.IOException;
import java.util.List;

public class ClopeAlgorithmHandler {

    public void start(String filename, double repulsion) throws IOException {
        List<Transaction> transactionList = readTransactions(filename);
        List<Cluster> clusters = clusteringClope(transactionList, repulsion);
        outputClusters(clusters);
    }

    private List<Transaction> readTransactions(String filename) throws IOException {
        return new FileReader(filename).read();
    }

    private List<Cluster> clusteringClope(List<Transaction> transactions, double repulsion) throws IllegalArgumentException {
        if (transactions.isEmpty()) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_COUNT_SOURCE_TRANSACTIONS.getMessage());
        }
        return new Clope().clustering(transactions, repulsion);
    }

    private void outputClusters(List<Cluster> clusters) {
        new ConsoleWriter().write(clusters);
    }
}
