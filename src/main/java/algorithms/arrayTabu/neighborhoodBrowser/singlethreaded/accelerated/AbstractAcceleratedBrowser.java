package algorithms.arrayTabu.neighborhoodBrowser.singlethreaded.accelerated;

import algorithms.arrayTabu.neighborhoodBrowser.singlethreaded.basic.AbstractBasicBrowser;

public abstract class AbstractAcceleratedBrowser extends AbstractBasicBrowser {
    @Override
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
                newPermutationValue = updateValue(currentPermutationValue, i, j);

                if (tabuIteration - tabuList[i][j] <= tabuListLength) {
                    continue; //TODO: ASPIRATION
                }

                if (newPermutationValue < bestNeighborPermutationValue) {
                    neighborhoodGeneratingFunction(currentPermutationValue, i, j);
                    System.arraycopy(newPermutation, 0, bestNeighborPermutation, 0, newPermutation.length);
                    bestNeighborPermutationValue = newPermutationValue;
                    bestNeighborPermutationI = i;
                    bestNeighborPermutationJ = j;
                }
            }
        }
    }

    public abstract double updateValue(double currentPermutationValue, int i, int j);
}
