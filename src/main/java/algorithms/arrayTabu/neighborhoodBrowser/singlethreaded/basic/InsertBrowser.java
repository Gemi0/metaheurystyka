package algorithms.arrayTabu.neighborhoodBrowser.singlethreaded.basic;

import algorithms.arrayTabu.neighborhoodBrowser.Util;
import main.TSPData;

public class InsertBrowser extends AbstractBasicBrowser{

    @Override
    public void prepareMemory(int[] currentPermutation, long[][] tabuList, int tabuListLength, TSPData data) {
        super.prepareMemory(currentPermutation, tabuList, tabuListLength, data);
        this.symmetric = false;
    }

    //insert i-th element on j-th position
    @Override
    protected void neighborhoodGeneratingFunction(double currentPermutationValue, int i, int j) {
        Util.insert(currentPermutation, newPermutation, i, j);
    }
}
