package algorithms.arrayTabu.neighborhoodBrowser.basic;

import algorithms.Utils;

public class InsertBrowser extends AbstractBasicBrowser{

    @Override
    public void browse(long tabuIteration, double currentPermutationValue, double bestPermutationValue) {
        bestNeighborPermutationValue = Double.MAX_VALUE;
        bestNeighborPermutationI = -1;
        bestNeighborPermutationJ = -1;

        double newPermutationValue = Double.MAX_VALUE;

        for (int i = 0; i < currentPermutation.length; i++) {
            for (int j = 0; j < currentPermutation.length; j++) {
                if(i == j)
                    continue;

                neighborhoodGeneratingFunction(currentPermutationValue, i, j);
                newPermutationValue = Utils.routeLength(newPermutation, data);

                if (tabuIteration - tabuList[i][j] <= tabuListLength) {
                    continue;//TODO: ASPIRATION
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

    //insert i-th element on j-th position
    @Override
    protected void neighborhoodGeneratingFunction(double currentPermutationValue, int i, int j) {
        if (i < j) {
            System.arraycopy(currentPermutation, 0, newPermutation, 0, i);
            System.arraycopy(currentPermutation, i + 1, newPermutation, i, j - i);
            newPermutation[j] = currentPermutation[i];
            if (currentPermutation.length - (j + 1) >= 0)
                System.arraycopy(currentPermutation, j + 1, newPermutation, j + 1, currentPermutation.length - (j + 1));
            return;
        }
        if (i > j) {
            System.arraycopy(currentPermutation, 0, newPermutation, 0, j);
            newPermutation[j] = currentPermutation[i];
            System.arraycopy(currentPermutation, j, newPermutation, j + 1, i - j);
            if (currentPermutation.length - (i + 1) >= 0)
                System.arraycopy(currentPermutation, i + 1, newPermutation, i + 1, currentPermutation.length - (i + 1));
            return;
        }
        System.arraycopy(currentPermutation, 0, newPermutation, 0, currentPermutation.length);
    }
}
