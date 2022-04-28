package algorithms.arrayTabu.neighborhoodBrowser.multithreaded.basic;

import algorithms.arrayTabu.neighborhoodBrowser.Util;
import main.TSPData;

public class InsertMultithreadedBrowser extends AbstractBasicMultithreadedBrowser{
    @Override
    public void prepareMemory(int[] currentPermutation, long[][] tabuList, int tabuListLength, TSPData data) {
        super.prepareMemory(currentPermutation, tabuList, tabuListLength, data);
        this.symmetric = false;
    }

    @Override
    protected void neighborhoodGeneratingFunction(double currentPermutationValue, int i, int j, int index) {
        Util.insert(currentPermutation, newPermutation[index], i, j);
    }
}
