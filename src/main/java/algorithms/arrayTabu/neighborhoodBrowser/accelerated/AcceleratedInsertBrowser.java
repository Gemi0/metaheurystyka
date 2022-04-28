package algorithms.arrayTabu.neighborhoodBrowser.accelerated;

public class AcceleratedInsertBrowser extends AbstractAcceleratedBrowser{
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
    @Override
    public double updateValue(double currentPermutationValue, int i, int j) {
        if (data.distance.length < 2)
            return 0;

        if(currentPermutation.length == 2)
            return currentPermutationValue;

        if((i == 0 && j == currentPermutation.length - 1) || (j == 0 && i == currentPermutation.length - 1)) {
            return currentPermutationValue;
        }

        if(i == j)
            return currentPermutationValue;

        if(i > j) {
            currentPermutationValue -= data.distance[currentPermutation[(i - 1 + currentPermutation.length)%currentPermutation.length]][currentPermutation[i]]
                    + data.distance[currentPermutation[i]][currentPermutation[(i + 1) % currentPermutation.length]]
                    + data.distance[currentPermutation[(j - 1 + currentPermutation.length)%currentPermutation.length]][currentPermutation[j]];
            currentPermutationValue += data.distance[currentPermutation[(i - 1 + currentPermutation.length)%currentPermutation.length]][(i + 1) % currentPermutation.length]
                    + data.distance[currentPermutation[i]][currentPermutation[j]]
                    + data.distance[currentPermutation[(j - 1 + currentPermutation.length)%currentPermutation.length]][currentPermutation[i]];
        } else {
            currentPermutationValue -= data.distance[currentPermutation[(i - 1 + currentPermutation.length)%currentPermutation.length]][currentPermutation[i]]
                    + data.distance[currentPermutation[i]][currentPermutation[(i + 1) % currentPermutation.length]]
                    + data.distance[currentPermutation[j]][currentPermutation[(j + 1)%currentPermutation.length]];
            currentPermutationValue += data.distance[currentPermutation[(i - 1 + currentPermutation.length)%currentPermutation.length]][currentPermutation[(i + 1) % currentPermutation.length]]
                    + data.distance[currentPermutation[j]][currentPermutation[i]]
                    + data.distance[currentPermutation[i]][currentPermutation[(j + 1)%currentPermutation.length]];
        }
            return Math.round(currentPermutationValue * 10000) / 10000.0;
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
