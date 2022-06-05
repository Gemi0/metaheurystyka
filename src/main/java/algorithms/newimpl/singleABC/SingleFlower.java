package algorithms.newimpl.singleABC;

public class SingleFlower {

    private int[] permutation;
    private double permutationValue;
    private int retriesCounter = 0;

    public SingleFlower(int[] permutation, double permutationValue) {
        setPermutation(permutation, permutationValue);
    }

    public void setPermutation(int[] permutation, double permutationValue) {
        this.permutation = permutation;
        this.permutationValue = permutationValue;
        retriesCounter = 0;
    }

    public int[] getPermutation() {
        return permutation;
    }

    public double getPermutationValue() {
        return permutationValue;
    }

    public void increaseCounter() {
        retriesCounter++;
    }

    public int getRetriesCounter() {
        return retriesCounter;
    }
}
