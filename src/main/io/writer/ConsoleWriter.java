package main.io.writer;

import main.app.models.Cluster;

import java.util.List;

public class ConsoleWriter {

    public void write(List<Cluster> clusters) {
        clusters.forEach(System.out::println);
    }
}
