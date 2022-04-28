package algorithms.arrayTabu.neighborhoodBrowser.basic;

public class InvertBrowser extends AbstractBasicBrowser{
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
