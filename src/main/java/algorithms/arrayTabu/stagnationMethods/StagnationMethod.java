package algorithms.arrayTabu.stagnationMethods;

public abstract class StagnationMethod {

    int[] bestPermutation;
    long[][] tabu;
    double bestPermutationValue = Double.MAX_VALUE;
    int stagnationCounter = 0;
    final int maxStagnation;

    public StagnationMethod(int maxStagnation){
        this.maxStagnation = maxStagnation;
    }

    public boolean checkStagnation(double currentValue,int[] perm, int tabuLength, long[][] tabuList) {
        stagnationCounter++;
        if (stagnationCounter <= tabuLength) {
            tabu = tabuList;
        }
        if( currentValue < bestPermutationValue) {
            bestPermutation = perm;
            bestPermutationValue = currentValue;
            stagnationCounter = 0;
            return false;
        }
        return stagnationCounter >= maxStagnation;
    }

    public abstract int[] setPermutation();

    public abstract double setPermutationValue();

    public abstract long[][] setTabu();
}
