package algorithms.arrayTabu.neighborhoodBrowser.singlethreaded.accelerated;

import algorithms.arrayTabu.neighborhoodBrowser.Util;
import main.TSPData;

public class AcceleratedInsertBrowser extends AbstractAcceleratedBrowser{

    @Override
    public void prepareMemory(int[] currentPermutation, long[][] tabuList, int tabuListLength, TSPData data) {
        super.prepareMemory(currentPermutation, tabuList, tabuListLength, data);
        this.symmetric = false;
    }

    @Override
    public double updateValue(double currentPermutationValue, int i, int j) {
        return Util.updateInsertValue(currentPermutation, data, currentPermutationValue, i, j);
    }


    //insert i-th element on j-th position
    @Override
    protected void neighborhoodGeneratingFunction(double currentPermutationValue, int i, int j) {
        Util.insert(currentPermutation, newPermutation, i, j);
    }
}
