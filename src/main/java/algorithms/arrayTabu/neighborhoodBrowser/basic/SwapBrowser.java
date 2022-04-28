package algorithms.arrayTabu.neighborhoodBrowser.basic;

public class SwapBrowser extends AbstractBasicBrowser{
    @Override
    protected void neighborhoodGeneratingFunction(double currentPermutationValue, int i, int j) {
        System.arraycopy(currentPermutation, 0, newPermutation, 0, currentPermutation.length);
        int temp = newPermutation[i];
        newPermutation[i] = newPermutation[j];
        newPermutation[j] = temp;
    }
}
