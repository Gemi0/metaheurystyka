package algorithms.arrayTabu.stagnationMethods;

public class BestValue extends StagnationMethod {

    public BestValue(int maxStagnation) {
        super(maxStagnation);
    }

    @Override
    public int[] setPermutation() {
        return bestPermutation;
    }

    @Override
    public double setPermutationValue() {
        return bestPermutationValue;
    }

    @Override
    public long[][] setTabu() {
        return tabu;
    }
}
