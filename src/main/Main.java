package main;

import main.app.handler.ClopeAlgorithmHandler;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            ClopeAlgorithmHandler clopeAlgorithmHandler = new ClopeAlgorithmHandler();
            clopeAlgorithmHandler.start("agaricus-lepiota.data.txt", 2.6);
        } catch ( IOException | IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }
    }
}
