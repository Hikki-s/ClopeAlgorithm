package main.app.models;

import java.util.ArrayList;
import java.util.List;

public class Transaction {

    private int clusterPosition;
    private int clusterId;
    private List<String> items = new ArrayList<>();

    public Transaction(List<String> items) {
        this.clusterPosition = 0;
        this.clusterId = 0;
        this.items.addAll(items);
    }

    public int getClusterId() {
        return clusterId;
    }

    public List<String> getItems() {
        return items;
    }

    public void setClusterId(int clusterId) {
        this.clusterId = clusterId;
    }
    public void setClusterPosition(int clusterPosition) {
        this.clusterPosition = clusterPosition;
    }

    @Override
    public String toString() {
        return items.toString();
    }
}
