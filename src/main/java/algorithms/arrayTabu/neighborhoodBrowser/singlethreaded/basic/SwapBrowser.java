package algorithms.arrayTabu.neighborhoodBrowser.singlethreaded.basic;

import algorithms.arrayTabu.neighborhoodBrowser.Util;

public class SwapBrowser extends AbstractBasicBrowser{
    @Override
    protected void neighborhoodGeneratingFunction(double currentPermutationValue, int i, int j) {
        Util.swap(currentPermutation, newPermutation, i, j);
    }
}
