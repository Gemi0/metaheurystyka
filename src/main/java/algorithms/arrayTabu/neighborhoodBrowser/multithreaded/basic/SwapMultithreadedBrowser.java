package algorithms.arrayTabu.neighborhoodBrowser.multithreaded.basic;

import algorithms.arrayTabu.neighborhoodBrowser.Util;

public class SwapMultithreadedBrowser extends AbstractBasicMultithreadedBrowser{
    @Override
    protected void neighborhoodGeneratingFunction(double currentPermutationValue, int i, int j, int index) {
        Util.swap(currentPermutation, newPermutation[index], i, j);
    }
}
