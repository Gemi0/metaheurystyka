package algorithms.arrayTabu.neighborhoodBrowser.multithreaded.basic;

public class InvertMultithreadedBrowser extends AbstractBasicMultithreadedBrowser{
    @Override
    protected void neighborhoodGeneratingFunction(double currentPermutationValue, int i, int j, int index) {
        if (i >= 0)
            System.arraycopy(currentPermutation, 0, newPermutation[index], 0, i);
        for (int x = i; x <= j; x++) {
            newPermutation[index][x] = currentPermutation[j - x + i];
        }
        if (currentPermutation.length - (j + 1) >= 0)
            System.arraycopy(currentPermutation, j + 1, newPermutation[index], j + 1, currentPermutation.length - (j + 1));
    }
}
