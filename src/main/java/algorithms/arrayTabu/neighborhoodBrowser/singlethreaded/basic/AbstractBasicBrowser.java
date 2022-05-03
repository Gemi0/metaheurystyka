package algorithms.arrayTabu.neighborhoodBrowser.singlethreaded.basic;

import algorithms.Utils;
import algorithms.arrayTabu.neighborhoodBrowser.NeighborhoodBrowser;
import main.TSPData;

public abstract class AbstractBasicBrowser implements NeighborhoodBrowser {

    @Override
    public void cleanup() {
        //DO NOTHING
    }

    protected boolean symmetric;
    protected int[] currentPermutation;
    protected int[] bestNeighborPermutation;
    protected int[] newPermutation;
    protected long[][] tabuList;
    protected int tabuListLength;
    protected TSPData data;

    protected double bestNeighborPermutationValue;
    protected int bestNeighborPermutationI;
    protected int bestNeighborPermutationJ;

    public void prepareMemory(int[] currentPermutation, long[][] tabuList, int tabuListLength, TSPData data) {
        this.currentPermutation = currentPermutation;
        this.bestNeighborPermutation = new int[currentPermutation.length];
        this.newPermutation = new int[currentPermutation.length];
        this.tabuList = tabuList;
        this.tabuListLength = tabuListLength;
        this.data = data;
        this.symmetric = true;
    }

    public void browse(long tabuIteration, double currentPermutationValue, double bestPermutationValue) {
        bestNeighborPermutationValue = Double.MAX_VALUE;
        bestNeighborPermutationI = -1;
        bestNeighborPermutationJ = -1;

        double newPermutationValue = Double.MAX_VALUE;

        for (int i = 0; i < currentPermutation.length; i++) {
            int j = i + 1;
            if(!symmetric){
                j = 0;
            }
            for (; j < currentPermutation.length; j++) {
                if(i == j) {
                    continue;
                }
                neighborhoodGeneratingFunction(currentPermutationValue, i, j);
                newPermutationValue = Utils.routeLength(newPermutation, data);

                if (tabuIteration - tabuList[i][j] <= tabuListLength) {
//                    if (newPermutationValue < bestPermutationValue) {
//                        System.arraycopy(newPermutation, 0, bestNeighborPermutation, 0, newPermutation.length);
//                        bestNeighborPermutationValue = newPermutationValue;
//                        bestNeighborPermutationI = i;
//                        bestNeighborPermutationJ = j;
//                    } //TODO aspiration
                    continue;
                }

                if (newPermutationValue < bestNeighborPermutationValue) {
                    System.arraycopy(newPermutation, 0, bestNeighborPermutation, 0, newPermutation.length);
                    bestNeighborPermutationValue = newPermutationValue;
                    bestNeighborPermutationI = i;
                    bestNeighborPermutationJ = j;
                }
            }
        }
    }

    public int getBestNeighborPermutationI() {
        return bestNeighborPermutationI;
    }

    public int getBestNeighborPermutationJ() {
        return bestNeighborPermutationJ;
    }

    public double getBestNeighborPermutationValue() {
        return bestNeighborPermutationValue;
    }

    public int[] getBestNeighborPermutationReference() {
        return bestNeighborPermutation;
    }

    protected abstract void neighborhoodGeneratingFunction(double currentPermutationValue, int i, int j);

}
