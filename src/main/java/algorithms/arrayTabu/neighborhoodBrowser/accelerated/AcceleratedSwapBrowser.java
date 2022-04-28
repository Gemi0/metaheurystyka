package algorithms.arrayTabu.neighborhoodBrowser.accelerated;

public class AcceleratedSwapBrowser extends AbstractAcceleratedBrowser {
    @Override
    public double updateValue(double currentPermutationValue, int i, int j) {
        if (data.distance.length < 2)
            return 0;

        if(i == j || currentPermutation.length == 2)
            return currentPermutationValue;

        int x = Math.min(i, j);
        int y = Math.max(i, j);

        if(x == 0 && y == currentPermutation.length - 1){
            currentPermutationValue -= data.distance[currentPermutation[x]][currentPermutation[x+1]] + data.distance[currentPermutation[y-1]][currentPermutation[y]] + data.distance[currentPermutation[y]][currentPermutation[x]];
            currentPermutationValue += data.distance[currentPermutation[y]][currentPermutation[x+1]] + data.distance[currentPermutation[y-1]][currentPermutation[x]] + data.distance[currentPermutation[x]][currentPermutation[y]];
            return Math.round(currentPermutationValue * 10000) / 10000.0;
        }

        if(y - x == 1) {
            currentPermutationValue -= (data.distance[currentPermutation[(x - 1 + currentPermutation.length) % currentPermutation.length]][currentPermutation[x]]
                    + data.distance[currentPermutation[x]][currentPermutation[y]]
                    + data.distance[currentPermutation[y]][currentPermutation[(y + 1) % currentPermutation.length]]);
            currentPermutationValue += (data.distance[currentPermutation[(x - 1 + currentPermutation.length) % currentPermutation.length]][currentPermutation[y]]
                    + data.distance[currentPermutation[y]][currentPermutation[x]]
                    + data.distance[currentPermutation[x]][currentPermutation[(y + 1) % currentPermutation.length]]);
            return Math.round(currentPermutationValue * 10000) / 10000.0;
        }


        currentPermutationValue -= (data.distance[currentPermutation[(i - 1 + currentPermutation.length) % currentPermutation.length]][currentPermutation[i]]
                + data.distance[currentPermutation[i]][currentPermutation[(i + 1) % currentPermutation.length]]
                + data.distance[currentPermutation[(j - 1 + currentPermutation.length) % currentPermutation.length]][currentPermutation[j]]
                + data.distance[currentPermutation[j]][currentPermutation[(j + 1) % currentPermutation.length]]);
        currentPermutationValue += (data.distance[currentPermutation[(i - 1 + currentPermutation.length) % currentPermutation.length]][currentPermutation[j]]
                + data.distance[currentPermutation[j]][currentPermutation[(i + 1) % currentPermutation.length]]
                + data.distance[currentPermutation[(j - 1 + currentPermutation.length) % currentPermutation.length]][currentPermutation[i]]
                + data.distance[currentPermutation[i]][currentPermutation[(j + 1) % currentPermutation.length]]);
        return Math.round(currentPermutationValue * 10000) / 10000.0;
    }

    @Override
    protected void neighborhoodGeneratingFunction(double currentPermutationValue, int i, int j) {
        System.arraycopy(currentPermutation, 0, newPermutation, 0, currentPermutation.length);
        int temp = newPermutation[i];
        newPermutation[i] = newPermutation[j];
        newPermutation[j] = temp;
    }
}
