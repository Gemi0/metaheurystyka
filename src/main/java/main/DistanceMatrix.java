package main;

import java.io.File;

public class DistanceMatrix {
    private double[][] distance;

    public void setMatrix(File file) {
        distance = FileParser.parseFile(file);
    }

    public double[][] getMatrix() {
        return distance;
    }

    public double get(int first, int second) {
        return distance[first][second];
    }

    public int getMatrixSize() {
        return distance.length;
    }
}
