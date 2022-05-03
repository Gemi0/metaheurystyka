package algorithms.arrayTabu.neighborhoodBrowser;

import main.TSPData;

public interface NeighborhoodBrowser {
    void prepareMemory(int[] currentPermutation, long[][] tabuList, int tabuListLength, TSPData data, boolean aspirationEnabled);
    void browse(long tabuIteration, double currentPermutationValue, double bestPermutationValue);
    int getBestNeighborPermutationI();
    int getBestNeighborPermutationJ();
    double getBestNeighborPermutationValue();
    int[] getBestNeighborPermutationReference();
    void cleanup();
}
