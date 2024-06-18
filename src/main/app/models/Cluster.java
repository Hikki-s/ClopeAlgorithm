package main.app.models;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Cluster {

    private int id;
    private int width;
    private int area;
    private Map<String, Integer> chart;

    private final List<Transaction> transactions = new ArrayList<>();

    public Cluster(int id, Transaction transaction) {
        this.id = id;
        this.transactions.add(transaction);
        this.area = transaction.getItems().size();
        this.chart = getClustersChart();
        this.width = this.chart.size();
        transaction.setClusterId(id);
    }

    public void add(Transaction transaction) {
        addItemsToChart(transaction);

        transaction.setClusterId(this.id);
        transaction.setClusterPosition(this.transactions.size());

        transactions.add(transaction);
        updateAreaAndWidth(transaction.getItems().size());
    }

    public void remove(Transaction transaction) {
        delItemsFromChart(transaction);

        this.transactions.remove(transaction);
        transaction.setClusterId(-1);

        updateAreaAndWidth(-transaction.getItems().size());
    }

    private void updateAreaAndWidth(int changeArea) {
        this.area += changeArea;
        this.width = this.chart.size();
    }

    private void delItemsFromChart(Transaction transaction) {
        for (String item : transaction.getItems()) {
            if (!(this.chart.remove(item, 1)) && this.chart.containsKey(item)) {
                this.chart.put(item, this.getChart().get(item) - 1);
            }
        }
    }

    private void addItemsToChart(Transaction transaction) {
        for (String item : transaction.getItems()) {
            this.chart.merge(item, 1, Integer::sum);
        }
    }

    private Map<String, Integer> getClustersChart() {
        return getAllTransactionsItems().collect(Collectors.groupingBy(
                Function.identity(),
                Collectors.collectingAndThen(Collectors.counting(), Long::intValue)));
    }


    private Stream<String> getAllTransactionsItems() {
        return transactions.stream().map(Transaction::getItems).flatMap(Collection::stream);
    }

    private Map<String, Integer> getFirstColumnValuesCount() {
        Set<String> uniqueFirstColumnValues = new HashSet<>();

        for (Transaction transaction : transactions) {
            String zeroIndexValue = transaction.getItems().get(0);
            uniqueFirstColumnValues.add(zeroIndexValue);
        }

        Map<String, Integer> countMap = new HashMap<>();
        for (String value : uniqueFirstColumnValues) {
            int count = (int) transactions.stream()
                    .filter(t -> t.getItems().get(0).equals(value))
                    .count();
            countMap.put(value, count);
        }

        return countMap;
    }

    public int getWidth() {
        return width;
    }

    public int getArea() {
        return area;
    }

    public Map<String, Integer> getChart() {
        return chart;
    }

    public void setChart(Map<String, Integer> chart) {
        this.chart = chart;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    @Override
    public String toString() {

        return "Transactions: \n" + getTransactions() + "\n\n" +
                this.getClustersChart().entrySet().stream()
                        .map(k -> (k.getKey() + ": " + k.getValue()))
                        .collect(Collectors.joining("\n")) +
                "\n\nS: " + getArea() + "\tW: " + getWidth() +
                "\n\n" + getFirstColumnValuesCount().entrySet().stream()
                .map(k -> k.getKey() + ": " + k.getValue())
                .collect(Collectors.joining("\n")) +
                "\n";
    }

}
