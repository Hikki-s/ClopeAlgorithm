package main.app.models;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

public class Clope {
    List<Cluster> clusters = new ArrayList<>();

    public List<Cluster> clustering(List<Transaction> transactions, double repulsion) {
        initialization(transactions, repulsion);
        iterable(transactions, repulsion);

        return clearClusters();
    }

    private void initialization(List<Transaction> transactions, double repulsion) {
        transactions.forEach(item -> putToMaxProfitCluster(item, repulsion, false));
    }

    private void iterable(List<Transaction> transactions, double repulsion) {
        boolean moved = true;
        while (moved) {
            moved = shuffle(transactions, repulsion);
        }
    }

    private List<Cluster> clearClusters() {
        return clusters.stream()
                .filter((cluster -> !(cluster.getTransactions().isEmpty())))
                .collect(Collectors.toList());
    }

    private boolean shuffle(List<Transaction> transactions, double repulsion) {
        int oldClusterId;
        boolean result = false;

        for (Transaction transaction : transactions) {
            oldClusterId = transaction.getClusterId();
            this.clusters.get(oldClusterId).remove(transaction);
            putToMaxProfitCluster(transaction, repulsion, true);
            if (oldClusterId != transaction.getClusterId()) result = true;
        }

        return result;
    }

    private void putToMaxProfitCluster(Transaction transaction, double repulsion, boolean isRemove) {
        int newArea = transaction.getItems().size();
        int newWidth = new HashSet<>(transaction.getItems()).size();
        boolean isFirst = true;

        double bestDeltaProfit = 0;
        Cluster bestProfitCluster = null;
        double maxProfit = newArea / Math.pow(newWidth, repulsion);

        for (Cluster cluster : this.clusters) {
            double delta = getDeltaProfit(cluster, transaction, repulsion);
            if (isFirst || delta > bestDeltaProfit) {
                if (delta > maxProfit) {
                    cluster.add(transaction);
                    return;
                }
                bestDeltaProfit = delta;
                bestProfitCluster = cluster;
                isFirst = false;
            }
        }

        changeClusters(maxProfit, bestDeltaProfit, bestProfitCluster, transaction, isRemove);
    }

    private void changeClusters(double maxProfit, double deltaProfit,
                                Cluster cluster, Transaction transaction, boolean isRemove) {
        if ((deltaProfit >= maxProfit || deltaProfit > 0 && isRemove) && cluster != null) {
            cluster.add(transaction);
        } else {
            this.clusters.add(new Cluster(this.clusters.size(), transaction));
        }
    }

    private double getDeltaProfit(Cluster cluster, Transaction transaction, double repulsion) {
        int area = cluster.getArea() + transaction.getItems().size();
        int width = getNewWidth(cluster, transaction);

        double deltaProfit = area / Math.pow(width, repulsion);

        if (!cluster.getTransactions().isEmpty()) {
            int countTransactions = cluster.getTransactions().size();
            double profit = calculateProfit(cluster.getArea(),
                    cluster.getWidth(), countTransactions, repulsion);
            double profitNew = calculateProfit(area, width, countTransactions + 1, repulsion);
            return profitNew - profit;
        }

        return deltaProfit;
    }

    private double calculateProfit(int s, int w, int c, double repulsion) {
        return s * c / Math.pow(w, repulsion);
    }

    private int getNewWidth(Cluster cluster, Transaction transaction) {
        return cluster.getWidth() + (int) transaction.getItems().stream()
                .filter(item -> !(cluster.getChart().containsKey(item)))
                .count();
    }
}
