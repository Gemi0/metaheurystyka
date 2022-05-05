package algorithms.arrayTabu.neighborhoodBrowser.multithreaded.basic;

import algorithms.arrayTabu.neighborhoodBrowser.Util;
import main.TSPData;

public class InsertMultithreadedBrowser extends AbstractBasicMultithreadedBrowser{
    @Override
    public void prepareMemory(int[] currentPermutation, long[][] tabuList, int tabuListLength, TSPData data, boolean aspirationEnabled) {
        super.prepareMemory(currentPermutation, tabuList, tabuListLength, data, aspirationEnabled);
        this.symmetric = false;
    }

    @Override
    protected void neighborhoodGeneratingFunction(double currentPermutationValue, int i, int j, int index) {
        Util.insert(currentPermutation, newPermutation[index], i, j);
    }
}
