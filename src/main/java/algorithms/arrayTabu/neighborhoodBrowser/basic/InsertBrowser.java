package algorithms.arrayTabu.neighborhoodBrowser.basic;

import main.TSPData;

public class InsertBrowser extends AbstractBasicBrowser{

    @Override
    public void prepareMemory(int[] currentPermutation, long[][] tabuList, int tabuListLength, TSPData data) {
        super.prepareMemory(currentPermutation, tabuList, tabuListLength, data);
        this.symmetric = true;
    }

    //insert i-th element on j-th position
    @Override
    protected void neighborhoodGeneratingFunction(double currentPermutationValue, int i, int j) {
        if (i < j) {
            System.arraycopy(currentPermutation, 0, newPermutation, 0, i);
            System.arraycopy(currentPermutation, i + 1, newPermutation, i, j - i);
            newPermutation[j] = currentPermutation[i];
            if (currentPermutation.length - (j + 1) >= 0)
                System.arraycopy(currentPermutation, j + 1, newPermutation, j + 1, currentPermutation.length - (j + 1));
            return;
        }
        if (i > j) {
            System.arraycopy(currentPermutation, 0, newPermutation, 0, j);
            newPermutation[j] = currentPermutation[i];
            System.arraycopy(currentPermutation, j, newPermutation, j + 1, i - j);
            if (currentPermutation.length - (i + 1) >= 0)
                System.arraycopy(currentPermutation, i + 1, newPermutation, i + 1, currentPermutation.length - (i + 1));
            return;
        }
        System.arraycopy(currentPermutation, 0, newPermutation, 0, currentPermutation.length);
    }
}
