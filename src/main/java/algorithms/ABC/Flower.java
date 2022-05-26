package algorithms.ABC;

import algorithms.Utils;
import main.TSPData;

public class Flower {

    private int counter;
    private final int[] permutation;
    private double permutationValue;

    public Flower(int[] permutation, TSPData data) {
        this.counter = 0;
        this.permutation = permutation;
        this.permutationValue = Utils.routeLength(permutation, data);
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
