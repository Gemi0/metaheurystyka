package algorithms.arrayTabu.neighborhoodBrowser.singlethreaded.basic;

import algorithms.arrayTabu.neighborhoodBrowser.Util;

public class InvertBrowser extends AbstractBasicBrowser{
    @Override
    protected void neighborhoodGeneratingFunction(double currentPermutationValue, int i, int j) {
        Util.invert(currentPermutation, newPermutation, i, j);
    }
}
