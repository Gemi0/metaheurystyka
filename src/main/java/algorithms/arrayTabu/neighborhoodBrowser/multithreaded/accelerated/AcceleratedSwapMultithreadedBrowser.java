package algorithms.arrayTabu.neighborhoodBrowser.multithreaded.accelerated;

import algorithms.arrayTabu.neighborhoodBrowser.Util;

public class AcceleratedSwapMultithreadedBrowser extends AbstractAcceleratedMultithreadedBrowser{
    @Override
    public double updateValue(double currentPermutationValue, int i, int j) {
        return Util.updateSwapValue(currentPermutation, data, currentPermutationValue, i, j);
    }
    @Override
    protected void neighborhoodGeneratingFunction(double currentPermutationValue, int i, int j, int index) {
        Util.swap(currentPermutation, newPermutation[index], i, j);
    }
}
