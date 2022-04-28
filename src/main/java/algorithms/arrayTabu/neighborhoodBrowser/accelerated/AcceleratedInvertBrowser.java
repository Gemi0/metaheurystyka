package algorithms.arrayTabu.neighborhoodBrowser.accelerated;

public class AcceleratedInvertBrowser extends AbstractAcceleratedBrowser{
    @Override
    public double updateValue(double currentPermutationValue, int i, int j) {
        if (data.distance.length < 2)
            return 0;

        double retVal = 0;
        if (i != 0 && j != (data.distance.length - 1)) {
            retVal = currentPermutationValue - data.distance[currentPermutation[i - 1]][currentPermutation[i]] + data.distance[currentPermutation[i - 1]][currentPermutation[j]] - data.distance[currentPermutation[j]][currentPermutation[j + 1]] + data.distance[currentPermutation[i]][currentPermutation[j + 1]];
        } else if (i != 0 && j == (data.distance.length - 1)) {
            retVal = currentPermutationValue - data.distance[currentPermutation[i - 1]][currentPermutation[i]] + data.distance[currentPermutation[i - 1]][currentPermutation[j]] - data.distance[currentPermutation[j]][currentPermutation[0]] + data.distance[currentPermutation[i]][currentPermutation[0]];
        } else if (i == 0 && j != (data.distance.length - 1)) {
            retVal = currentPermutationValue - data.distance[currentPermutation[data.distance.length - 1]][currentPermutation[i]] + data.distance[currentPermutation[data.distance.length - 1]][currentPermutation[j]] - data.distance[currentPermutation[j]][currentPermutation[j + 1]] + data.distance[currentPermutation[i]][currentPermutation[j + 1]];
        } else
            retVal = currentPermutationValue;
        return Math.round(retVal * 10000) / 10000.0;
    }

    @Override
    protected void neighborhoodGeneratingFunction(double currentPermutationValue, int i, int j) {
        if (i >= 0)
            System.arraycopy(currentPermutation, 0, newPermutation, 0, i);
        for (int x = i; x <= j; x++) {
            newPermutation[x] = currentPermutation[j - x + i];
        }
        if (currentPermutation.length - (j + 1) >= 0)
            System.arraycopy(currentPermutation, j + 1, newPermutation, j + 1, currentPermutation.length - (j + 1));
    }
}
