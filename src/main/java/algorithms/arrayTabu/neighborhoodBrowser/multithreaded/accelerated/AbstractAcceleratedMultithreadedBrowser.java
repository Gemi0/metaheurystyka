package algorithms.arrayTabu.neighborhoodBrowser.multithreaded.accelerated;

import algorithms.Utils;
import algorithms.arrayTabu.neighborhoodBrowser.multithreaded.basic.AbstractBasicMultithreadedBrowser;

public abstract class AbstractAcceleratedMultithreadedBrowser extends AbstractBasicMultithreadedBrowser {
    @Override
    protected void browse(long tabuIteration, double currentPermutationValue, double bestPermutationValue, int start, int end, int index) {
        bestNeighborPermutationValue[index] = Double.MAX_VALUE;
        bestNeighborPermutationI[index] = -1;
        bestNeighborPermutationJ[index] = -1;

        double newPermutationValue = Double.MAX_VALUE;

        for (int i = start; i < end; i++) {
            int j = i + 1;
            if (!symmetric) {
                j = 0;
            }
            for (; j < currentPermutation.length; j++) {
                if (i == j) {
                    continue;
                }
                newPermutationValue = updateValue(currentPermutationValue, i, j);

                if (tabuIteration - tabuList[i][j] <= tabuListLength) {
                    continue;//TODO: ASPIRATION
                }

                if (newPermutationValue < bestNeighborPermutationValue[index]) {
                    neighborhoodGeneratingFunction(currentPermutationValue, i, j, index);
                    System.arraycopy(newPermutation[index], 0, bestNeighborPermutation[index], 0, newPermutation[index].length);
                    bestNeighborPermutationValue[index] = newPermutationValue;
                    bestNeighborPermutationI[index] = i;
                    bestNeighborPermutationJ[index] = j;
                }
            }
        }
    }

    public abstract double updateValue(double currentPermutationValue, int i, int j);
}
