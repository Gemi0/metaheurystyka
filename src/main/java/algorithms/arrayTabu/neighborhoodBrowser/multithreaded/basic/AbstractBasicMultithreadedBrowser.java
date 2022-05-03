package algorithms.arrayTabu.neighborhoodBrowser.multithreaded.basic;

import algorithms.Utils;
import algorithms.arrayTabu.neighborhoodBrowser.NeighborhoodBrowser;
import main.TSPData;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public abstract class AbstractBasicMultithreadedBrowser implements NeighborhoodBrowser {
    protected boolean symmetric;
    protected int[] currentPermutation;
    protected long[][] tabuList;
    protected int tabuListLength;
    protected TSPData data;
    protected boolean aspirationEnabled;

    protected int[][] bestNeighborPermutation;
    protected int[][] newPermutation;
    protected double[] bestNeighborPermutationValue;
    protected int[] bestNeighborPermutationI;
    protected int[] bestNeighborPermutationJ;
    protected Runnable runnableTasks[];

    protected long tabuIteration;
    protected double currentPermutationValue;
    protected double bestPermutationValue;
    protected ExecutorService executor;
    protected CountDownLatch latch;
    protected int portions;

    protected int bestIndex;

    public static final int THREAD_FACTOR = 2;

    public void prepareMemory(int[] currentPermutation, long[][] tabuList, int tabuListLength, TSPData data, boolean aspirationEnabled) {
        this.currentPermutation = currentPermutation;
        this.tabuList = tabuList;
        this.tabuListLength = tabuListLength;
        this.data = data;
        this.aspirationEnabled = aspirationEnabled;

        int threadsNumber = Runtime.getRuntime().availableProcessors();
        int increment = Math.max(currentPermutation.length / threadsNumber / THREAD_FACTOR, 1);
        portions = (int) Math.ceil(((double) currentPermutation.length) / ((double) increment));

        this.bestNeighborPermutation = new int[portions][currentPermutation.length];
        this.newPermutation = new int[portions][currentPermutation.length];
        this.bestNeighborPermutationValue = new double[portions];
        this.bestNeighborPermutationI = new int[portions];
        this.bestNeighborPermutationJ = new int[portions];
        this.runnableTasks = new Runnable[portions];

        executor = Executors.newFixedThreadPool(threadsNumber);
        int i = 0;
        int index = 0;
        while (i < currentPermutation.length) {
            int start = i;
            final int finalIndex = index;
            int end = Math.min(start + increment, currentPermutation.length);
            runnableTasks[index] = () -> {
                browse(this.tabuIteration, this.currentPermutationValue, this.bestPermutationValue, start, end, finalIndex);
                latch.countDown();
            };
            index++;
            i = end;
        }
        this.symmetric = true;
    }

    public void browse(long tabuIteration, double currentPermutationValue, double bestPermutationValue) {
        this.tabuIteration = tabuIteration;
        this.currentPermutationValue = currentPermutationValue;
        this.bestPermutationValue = bestPermutationValue;

        this.latch = new CountDownLatch(portions);

        for (Runnable r : runnableTasks)
            executor.submit(r);

        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.exit(-1);
        }

        double bestVal = Double.MAX_VALUE;
        for (int i = 0; i < portions; i++) {
            if (bestVal > bestNeighborPermutationValue[i]) {
                bestVal = bestNeighborPermutationValue[i];
                bestIndex = i;
            }
        }

    }


    protected void browse(long tabuIteration, double currentPermutationValue, double bestPermutationValue, int start, int end, int index) {
        bestNeighborPermutationValue[index] = Double.MAX_VALUE;
        bestNeighborPermutationI[index] = -1;
        bestNeighborPermutationJ[index] = -1;

        double newPermutationValue = Double.MAX_VALUE;

        for (int i = start; i < end; i++) {
            int j = i + 1;
            if (!symmetric) {
                j = 0;
            }
            for (; j < currentPermutation.length; j++) {
                if (i == j) {
                    continue;
                }
                neighborhoodGeneratingFunction(currentPermutationValue, i, j, index);
                newPermutationValue = Utils.routeLength(newPermutation[index], data);

                if (tabuIteration - tabuList[i][j] <= tabuListLength) {
                    if(!aspirationEnabled || newPermutationValue >= bestPermutationValue){
                        continue;
                    }
                }

                if (newPermutationValue < bestNeighborPermutationValue[index]) {
                    System.arraycopy(newPermutation[index], 0, bestNeighborPermutation[index], 0, newPermutation[index].length);
                    bestNeighborPermutationValue[index] = newPermutationValue;
                    bestNeighborPermutationI[index] = i;
                    bestNeighborPermutationJ[index] = j;
                }
            }
        }
    }

    public int getBestNeighborPermutationI() {
        return bestNeighborPermutationI[bestIndex];
    }

    public int getBestNeighborPermutationJ() {
        return bestNeighborPermutationJ[bestIndex];
    }

    public double getBestNeighborPermutationValue() {
        return bestNeighborPermutationValue[bestIndex];
    }

    public int[] getBestNeighborPermutationReference() {
        return bestNeighborPermutation[bestIndex];
    }

    protected abstract void neighborhoodGeneratingFunction(double currentPermutationValue, int i, int j, int index);

    @Override
    public void cleanup() {
        executor.shutdown();
    }
}
