package algorithms.ABC;

import algorithms.Utils;
import main.TSPData;

public class Flower {

    private int counter;
    private int[] permutation;
    private double permutationValue;
    private final TSPData data;

    public Flower(int[] permutation, TSPData data) {
        this.data = data;
        this.counter = 0;
        this.permutation = permutation;
        this.permutationValue = Utils.routeLength(permutation, data);
    }

    public void setPermutation(int[] permutation) {
        this.permutationValue = Utils.routeLength(permutation, data);
        this.permutation = permutation;
    }

    public void resetCounter() {
        this.counter = 0;
    }

    public void increaseCounter() {
        counter++;
    }

    public int getCounter() {
        return counter;
    }

    public int[] getPermutation() {
        return permutation;
    }

    public double getPermutationValue() {
        return permutationValue;
    }
}
