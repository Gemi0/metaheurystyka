package algorithms.arrayTabu;

import algorithms.Utils;
import algorithms.arrayTabu.neighborhoodBrowser.NeighborhoodBrowser;
import algorithms.arrayTabu.stopConditions.StopCondition;
import main.TSPData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Tabu {

    public static int[] tabuSearch(NeighborhoodBrowser browser, StopCondition condition, int[] startPermutation, TSPData data, int tabuListLength, boolean aspirationEnabled, long stagnationMaxIterationsWithoutImprovement) {
        final int[] currentPermutation = Arrays.copyOf(startPermutation, startPermutation.length);
        double currentPermutationValue = Utils.routeLength(currentPermutation, data);

        final int[] bestPermutation = Arrays.copyOf(currentPermutation, startPermutation.length);
        double bestPermutationValue = currentPermutationValue;

        final long[][] tabuList = new long[currentPermutation.length][currentPermutation.length];
        for (long[] row : tabuList)
            Arrays.fill(row, -(tabuListLength + 1));

        //Best Solutions
        ArrayList<int[]> bestSolutions = new ArrayList<>();
        ArrayList<Double> bestSolutionsValues = new ArrayList<>();

        //Number used to index tabu list
        long tabuIteration = 0;

        //Used for exit condition
        long algorithmIteration = 0;

        //Used for stagnation
        long stagnationIterationsWithoutImprovement = 0;
        long[][] stagnationList = new long[currentPermutation.length][currentPermutation.length];
        long STAGNATION_TABU_LIST_SNAPSHOT_ITERATION = tabuListLength;

                //Prepare memory (multi-threading support)
        browser.prepareMemory(currentPermutation, tabuList, tabuListLength, data, aspirationEnabled);

        long startTime = System.nanoTime();
        do {
            //Update stagnation tabu
            if(tabuIteration == STAGNATION_TABU_LIST_SNAPSHOT_ITERATION){
                for (int i = 0; i < stagnationList.length; i++) {
                    System.arraycopy(tabuList[i], 0, stagnationList[i], 0, tabuList[i].length);
                }
                browser.setAspirationEnabled(aspirationEnabled);
            }

            //Check for stagnation
            if(stagnationIterationsWithoutImprovement > stagnationMaxIterationsWithoutImprovement) {
                //Do stagnation procedure
                tabuIteration = 0;
                stagnationIterationsWithoutImprovement = 0;
                for (int i = 0; i < stagnationList.length; i++) {
                    System.arraycopy(stagnationList[i], 0, tabuList[i], 0, tabuList[i].length);
                }
                //Add best solution
                bestSolutions.add(Arrays.copyOf(bestPermutation, bestPermutation.length));
                bestSolutionsValues.add(bestPermutationValue);
                //Reset algorithm preserving tabu
                System.arraycopy(startPermutation, 0, currentPermutation, 0, startPermutation.length);
                currentPermutationValue = Utils.routeLength(currentPermutation, data);
                System.arraycopy(startPermutation, 0, bestPermutation, 0, bestPermutation.length);
                bestPermutationValue = currentPermutationValue;
                //Temporarily disable aspiration
                browser.setAspirationEnabled(false);
                continue;
            }

            //Generate the neighborhood and select the best candidate
            browser.browse(tabuIteration, currentPermutationValue, bestPermutationValue);

            //all entries in tabu
            if (browser.getBestNeighborPermutationI() == -1) {
                //Iterate tabu list
                tabuIteration++;
                //Iterate algorithm
                algorithmIteration++;
                //Increase stagnation
                stagnationIterationsWithoutImprovement++;
                //Just continue, to empty tabu list
                continue;
            }

            //Set the candidate to the current permutation, update the entry in the tabu list
            System.arraycopy(browser.getBestNeighborPermutationReference(), 0, currentPermutation, 0, currentPermutation.length);
            tabuList[browser.getBestNeighborPermutationI()][browser.getBestNeighborPermutationJ()] = tabuIteration;
            currentPermutationValue = browser.getBestNeighborPermutationValue();

            //If the candidate is better than best so far, swap it
            if (currentPermutationValue < bestPermutationValue) {
                System.arraycopy(currentPermutation, 0, bestPermutation, 0, currentPermutation.length);
                bestPermutationValue = currentPermutationValue;
            } else {
                //Increase stagnation
                stagnationIterationsWithoutImprovement++;
            }

            //Iterate tabu list
            tabuIteration++;

            //Iterate algorithm
            algorithmIteration++;

        } while (!condition.shouldStop(algorithmIteration, System.nanoTime() - startTime));

        bestSolutionsValues.add(bestPermutationValue);
        bestSolutions.add(Arrays.copyOf(bestPermutation, bestPermutation.length));

        browser.cleanup();

        Double returnBestValue = Collections.min(bestSolutionsValues);
        int returnIndex = bestSolutionsValues.indexOf(returnBestValue);
        return bestSolutions.get(returnIndex);
    }
}
