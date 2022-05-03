package algorithms.arrayTabu;

import algorithms.Utils;
import algorithms.arrayTabu.neighborhoodBrowser.NeighborhoodBrowser;
import algorithms.arrayTabu.stopConditions.StopCondition;
import main.TSPData;

import java.util.ArrayList;
import java.util.Arrays;

public class Tabu {

    public static int[] tabuSearch(NeighborhoodBrowser browser, StopCondition condition, int[] startPermutation, TSPData data, int tabuListLength, boolean aspirationEnabled) {
        final int[] currentPermutation = Arrays.copyOf(startPermutation, startPermutation.length);
        double currentPermutationValue = Utils.routeLength(currentPermutation, data);

        final int[] bestPermutation = Arrays.copyOf(currentPermutation, startPermutation.length);
        double bestPermutationValue = currentPermutationValue;

        final long[][] tabuList = new long[currentPermutation.length][currentPermutation.length];
        for (long[] row : tabuList)
            Arrays.fill(row, -(tabuListLength + 1));
        long tabuIteration = 0;

        browser.prepareMemory(currentPermutation, tabuList, tabuListLength, data, aspirationEnabled);

        long startTime = System.nanoTime();
        while (true) {
            //Generate the neighborhood and select the best candidate
            browser.browse(tabuIteration, currentPermutationValue, bestPermutationValue);

            //all entries in tabu
            //Just continue, to empty tabu list
            if(browser.getBestNeighborPermutationI() != -1) {
                //Set the candidate to the current permutation, update the entry in the tabu list
                System.arraycopy(browser.getBestNeighborPermutationReference(), 0, currentPermutation, 0, currentPermutation.length);
                tabuList[browser.getBestNeighborPermutationI()][browser.getBestNeighborPermutationJ()] = tabuIteration;
                currentPermutationValue = browser.getBestNeighborPermutationValue();

                //If the candidate is better than best so far, swap it
                if (currentPermutationValue < bestPermutationValue) {
                    System.arraycopy(currentPermutation, 0, bestPermutation, 0, currentPermutation.length);
                    bestPermutationValue = currentPermutationValue;
                }
            }

            //Iterate tabu list
            tabuIteration++;

            //Stop condition
            if (condition.shouldStop(tabuIteration, System.nanoTime() - startTime)) {
                break;
            }
        }
        browser.cleanup();
        return bestPermutation;
    }
}
