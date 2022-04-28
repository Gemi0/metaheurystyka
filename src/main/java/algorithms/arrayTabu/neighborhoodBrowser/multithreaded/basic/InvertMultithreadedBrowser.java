package algorithms.arrayTabu.neighborhoodBrowser.multithreaded.basic;

import algorithms.arrayTabu.neighborhoodBrowser.Util;

public class InvertMultithreadedBrowser extends AbstractBasicMultithreadedBrowser{
    @Override
    protected void neighborhoodGeneratingFunction(double currentPermutationValue, int i, int j, int index) {
        Util.invert(currentPermutation, newPermutation[index], i, j);
    }
}
