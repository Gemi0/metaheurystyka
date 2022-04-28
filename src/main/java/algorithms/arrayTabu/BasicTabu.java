package algorithms.arrayTabu;

import algorithms.Utils;
import algorithms.arrayTabu.neighborhoodBrowser.singlethreaded.accelerated.AcceleratedInsertBrowser;
import algorithms.arrayTabu.neighborhoodBrowser.singlethreaded.accelerated.AcceleratedInvertBrowser;
import algorithms.arrayTabu.neighborhoodBrowser.singlethreaded.accelerated.AcceleratedSwapBrowser;
import algorithms.arrayTabu.neighborhoodBrowser.singlethreaded.basic.InsertBrowser;
import algorithms.arrayTabu.neighborhoodBrowser.singlethreaded.basic.InvertBrowser;
import algorithms.arrayTabu.neighborhoodBrowser.NeighborhoodBrowser;
import algorithms.arrayTabu.neighborhoodBrowser.singlethreaded.basic.SwapBrowser;
import algorithms.arrayTabu.neighborhoodBrowser.multithreaded.accelerated.AcceleratedInvertMultithreadedBrowser;
import algorithms.arrayTabu.neighborhoodBrowser.multithreaded.basic.InvertMultithreadedBrowser;
import main.TSPData;

import java.util.Arrays;

public class BasicTabu {

    public static int[] tabuSearch(NeighborhoodBrowser browser, int[] startPermutation, TSPData data, int tabuListLength, long nsTimeLimit) {
        final int[] currentPermutation = Arrays.copyOf(startPermutation, startPermutation.length);
        double currentPermutationValue = Utils.routeLength(currentPermutation, data);

        final int[] bestPermutation = Arrays.copyOf(currentPermutation, startPermutation.length);
        double bestPermutationValue = currentPermutationValue;

        final long[][] tabuList = new long[currentPermutation.length][currentPermutation.length];
        for (long[] row : tabuList)
            Arrays.fill(row, -(tabuListLength + 1));
        long tabuIteration = 0;

        browser.prepareMemory(currentPermutation, tabuList, tabuListLength, data);

        long startTime = System.nanoTime();
        while (true) {
            //Generate the neighborhood and select the best candidate
            browser.browse(tabuIteration, currentPermutationValue, bestPermutationValue);

            //all entries in tabu
            if(browser.getBestNeighborPermutationI() == -1) {
                break;//TODO: DO something better
            }

            //Set the candidate to the current permutation, update the entry in the tabu list
            System.arraycopy(browser.getBestNeighborPermutationReference(), 0, currentPermutation, 0, currentPermutation.length);
            tabuList[browser.getBestNeighborPermutationI()][browser.getBestNeighborPermutationJ()] = tabuIteration;
            currentPermutationValue = browser.getBestNeighborPermutationValue();

            //If the candidate is better than best so far, swap it
            if (currentPermutationValue < bestPermutationValue) {
                System.arraycopy(currentPermutation, 0, bestPermutation, 0, currentPermutation.length);
                bestPermutationValue = currentPermutationValue;
            }

            //Iterate tabu list
            tabuIteration++;

            //Stop condition
            if (System.nanoTime() - startTime > nsTimeLimit) {
                break;
            }
        }
        browser.cleanup();
        return bestPermutation;
    }

    public static int[] tabuSearchInvert(int[] startPermutation, TSPData data, int tabuListLength, long nsTimeLimit) {
        return tabuSearch(new InvertBrowser(), startPermutation, data, tabuListLength, nsTimeLimit);
    }

    public static int[] tabuSearchSwap(int[] startPermutation, TSPData data, int tabuListLength, long nsTimeLimit) {
        return tabuSearch(new SwapBrowser(), startPermutation, data, tabuListLength, nsTimeLimit);
    }

    public static int[] tabuSearchInsertAcc(int[] startPermutation, TSPData data, int tabuListLength, long nsTimeLimit) {
        return tabuSearch(new AcceleratedInsertBrowser(), startPermutation, data, tabuListLength, nsTimeLimit);
    }

    public static int[] tabuSearchInvertAcc(int[] startPermutation, TSPData data, int tabuListLength, long nsTimeLimit) {
        return tabuSearch(new AcceleratedInvertBrowser(), startPermutation, data, tabuListLength, nsTimeLimit);
    }

    public static int[] tabuSearchSwapAcc(int[] startPermutation, TSPData data, int tabuListLength, long nsTimeLimit) {
        return tabuSearch(new AcceleratedSwapBrowser(), startPermutation, data, tabuListLength, nsTimeLimit);
    }

    public static int[] tabuSearchInsert(int[] startPermutation, TSPData data, int tabuListLength, long nsTimeLimit) {
        return tabuSearch(new InsertBrowser(), startPermutation, data, tabuListLength, nsTimeLimit);
    }

    public static int[] tabuSearchInvertMultithreaded(int[] startPermutation, TSPData data, int tabuListLength, long nsTimeLimit) {
        return tabuSearch(new InvertMultithreadedBrowser(), startPermutation, data, tabuListLength, nsTimeLimit);
    }

    public static int[] tabuSearchAcceleratedInvertMultithreaded(int[] startPermutation, TSPData data, int tabuListLength, long nsTimeLimit) {
        return tabuSearch(new AcceleratedInvertMultithreadedBrowser(), startPermutation, data, tabuListLength, nsTimeLimit);
    }
}
