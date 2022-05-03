package algorithms.arrayTabu.neighborhoodBrowser.multithreaded.accelerated;

import algorithms.arrayTabu.neighborhoodBrowser.Util;
import main.TSPData;

public class AcceleratedInsertMultithreadedBrowser extends AbstractAcceleratedMultithreadedBrowser{
    @Override
    public void prepareMemory(int[] currentPermutation, long[][] tabuList, int tabuListLength, TSPData data, boolean aspirationEnabled) {
        super.prepareMemory(currentPermutation, tabuList, tabuListLength, data, aspirationEnabled);
        super.symmetric = false;
    }

    @Override
    public double updateValue(double currentPermutationValue, int i, int j) {
        return Util.updateInsertValue(currentPermutation, data, currentPermutationValue, i, j);
    }
    @Override
    protected void neighborhoodGeneratingFunction(double currentPermutationValue, int i, int j, int index) {
        Util.insert(currentPermutation, newPermutation[index], i, j);
    }
}
