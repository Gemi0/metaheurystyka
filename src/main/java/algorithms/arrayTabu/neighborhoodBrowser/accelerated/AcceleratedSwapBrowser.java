package algorithms.arrayTabu.neighborhoodBrowser.accelerated;

import algorithms.arrayTabu.neighborhoodBrowser.Util;

public class AcceleratedSwapBrowser extends AbstractAcceleratedBrowser {
    @Override
    public double updateValue(double currentPermutationValue, int i, int j) {
        return Util.updateSwapValue(currentPermutation, data, currentPermutationValue, i, j);
    }

    @Override
    protected void neighborhoodGeneratingFunction(double currentPermutationValue, int i, int j) {
        Util.swap(currentPermutation, newPermutation, i, j);
    }
}
