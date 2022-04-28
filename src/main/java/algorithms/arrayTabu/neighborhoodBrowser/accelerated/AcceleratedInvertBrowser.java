package algorithms.arrayTabu.neighborhoodBrowser.accelerated;

import algorithms.arrayTabu.neighborhoodBrowser.Util;

public class AcceleratedInvertBrowser extends AbstractAcceleratedBrowser{
    @Override
    public double updateValue(double currentPermutationValue, int i, int j) {
        return Util.updateInvertValue(currentPermutation, data, currentPermutationValue, i, j);
    }

    @Override
    protected void neighborhoodGeneratingFunction(double currentPermutationValue, int i, int j) {
        Util.invert(currentPermutation, newPermutation, i, j);
    }
}
