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
import algorithms.arrayTabu.stagnationMethods.BestValue;
import algorithms.arrayTabu.stagnationMethods.StagnationMethod;
import algorithms.arrayTabu.stopCondition.StopCondition;
import algorithms.arrayTabu.stopCondition.TimeStop;
import main.TSPData;

import java.util.Arrays;

public class BasicTabu {

    public static int[] tabuSearch(NeighborhoodBrowser browser, StagnationMethod stagnationMethod, StopCondition stopCondition, int[] startPermutation, TSPData data, int tabuListLength) {
        final int[] currentPermutation = Arrays.copyOf(startPermutation, startPermutation.length);
        double currentPermutationValue = Utils.routeLength(currentPermutation, data);

        final int[] bestPermutation = Arrays.copyOf(currentPermutation, startPermutation.length);
        double bestPermutationValue = currentPermutationValue;

        long[][] tabuList = new long[currentPermutation.length][currentPermutation.length];
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
                System.arraycopy(currentPermutation, 0, stagnationMethod.setPermutation(), 0, currentPermutation.length);
                bestPermutationValue = stagnationMethod.setPermutationValue();
                tabuList = stagnationMethod.setTabu();
                //TODO: IDK may do something different here
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

            //stagnation
            if (stagnationMethod.checkStagnation(currentPermutationValue, currentPermutation, tabuListLength, tabuList)) {
                int[] temp = stagnationMethod.setPermutation();
                System.arraycopy(currentPermutation, 0, temp, 0, currentPermutation.length);
                bestPermutationValue = stagnationMethod.setPermutationValue();
                tabuList = stagnationMethod.setTabu();
            }

            //Iterate tabu list
            tabuIteration++;

            //Stop condition
            if (stopCondition.checkStop(startTime, -1)) {
                break;
            }
        }
        browser.cleanup();
        return bestPermutation;
    }

    public static int[] tabuSearchInvert(int[] startPermutation, TSPData data, int tabuListLength, long nsTimeLimit, int maxStagnation) {
        return tabuSearch(new InvertBrowser(), new BestValue(maxStagnation), new TimeStop(nsTimeLimit), startPermutation, data, tabuListLength);
    }

    public static int[] tabuSearchSwap(int[] startPermutation, TSPData data, int tabuListLength, long nsTimeLimit, int maxStagnation) {
        return tabuSearch(new SwapBrowser(), new BestValue(maxStagnation), new TimeStop(nsTimeLimit), startPermutation, data, tabuListLength);
    }

    public static int[] tabuSearchInsertAcc(int[] startPermutation, TSPData data, int tabuListLength, long nsTimeLimit, int maxStagnation) {
        return tabuSearch(new AcceleratedInsertBrowser(),new BestValue(maxStagnation), new TimeStop(nsTimeLimit),startPermutation, data, tabuListLength);
    }

    public static int[] tabuSearchInvertAcc(int[] startPermutation, TSPData data, int tabuListLength, long nsTimeLimit, int maxStagnation) {
        return tabuSearch(new AcceleratedInvertBrowser(),new BestValue(maxStagnation), new TimeStop(nsTimeLimit),startPermutation, data, tabuListLength);
    }

    public static int[] tabuSearchSwapAcc(int[] startPermutation, TSPData data, int tabuListLength, long nsTimeLimit, int maxStagnation) {
        return tabuSearch(new AcceleratedSwapBrowser(),new BestValue(maxStagnation),new TimeStop(nsTimeLimit), startPermutation, data, tabuListLength);
    }

    public static int[] tabuSearchInsert(int[] startPermutation, TSPData data, int tabuListLength, long nsTimeLimit, int maxStagnation) {
        return tabuSearch(new InsertBrowser(),new BestValue(maxStagnation),new TimeStop(nsTimeLimit), startPermutation, data, tabuListLength);
    }

    public static int[] tabuSearchInvertMultithreaded(int[] startPermutation, TSPData data, int tabuListLength, long nsTimeLimit, int maxStagnation) {
        return tabuSearch(new InvertMultithreadedBrowser(), new BestValue(maxStagnation),new TimeStop(nsTimeLimit),startPermutation, data, tabuListLength);
    }

    public static int[] tabuSearchAcceleratedInvertMultithreaded(int[] startPermutation, TSPData data, int tabuListLength, long nsTimeLimit, int maxStagnation) {
        return tabuSearch(new AcceleratedInvertMultithreadedBrowser(),new BestValue(maxStagnation),new TimeStop(nsTimeLimit), startPermutation, data, tabuListLength);
    }
}
