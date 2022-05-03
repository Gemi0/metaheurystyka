package benchmark;

import algorithms.*;
import algorithms.arrayTabu.Tabu;
import algorithms.arrayTabu.neighborhoodBrowser.multithreaded.accelerated.AcceleratedInsertMultithreadedBrowser;
import algorithms.arrayTabu.neighborhoodBrowser.multithreaded.accelerated.AcceleratedInvertMultithreadedBrowser;
import algorithms.arrayTabu.neighborhoodBrowser.multithreaded.accelerated.AcceleratedSwapMultithreadedBrowser;
import algorithms.arrayTabu.neighborhoodBrowser.multithreaded.basic.InsertMultithreadedBrowser;
import algorithms.arrayTabu.neighborhoodBrowser.multithreaded.basic.InvertMultithreadedBrowser;
import algorithms.arrayTabu.neighborhoodBrowser.multithreaded.basic.SwapMultithreadedBrowser;
import algorithms.arrayTabu.neighborhoodBrowser.singlethreaded.accelerated.AcceleratedInsertBrowser;
import algorithms.arrayTabu.neighborhoodBrowser.singlethreaded.accelerated.AcceleratedInvertBrowser;
import algorithms.arrayTabu.neighborhoodBrowser.singlethreaded.accelerated.AcceleratedSwapBrowser;
import algorithms.arrayTabu.neighborhoodBrowser.singlethreaded.basic.InsertBrowser;
import algorithms.arrayTabu.neighborhoodBrowser.singlethreaded.basic.InvertBrowser;
import algorithms.arrayTabu.neighborhoodBrowser.singlethreaded.basic.SwapBrowser;
import algorithms.arrayTabu.stopConditions.IterationStopCondition;
import algorithms.arrayTabu.stopConditions.TimeStopCondition;
import main.TSPData;

public class Benchmark {


    public static Result runKRandom(TSPData data, int k) {
        //System.gc();
        long startTime = System.nanoTime();
        int[] permutation = KRandom.kRandom(data, k);
        long endTime = System.nanoTime();

        return new Result(endTime - startTime, Utils.routeLength(permutation, data));
    }

    public static Result runNeighbor(TSPData data, int start) {
        //System.gc();
        long startTime = System.nanoTime();
        int[] permutation = Neighbor.neighbor(data, start);
        long endTime = System.nanoTime();

        return new Result(endTime - startTime, Utils.routeLength(permutation, data));
    }

    public static Result runNeighborExtended(TSPData data) {
        //System.gc();
        long startTime = System.nanoTime();
        int[] permutation = NeighborExtended.neighborExtended(data);
        long endTime = System.nanoTime();

        return new Result(endTime - startTime, Utils.routeLength(permutation, data));
    }

    public static Result runTwoOpt(TSPData data) {
        //System.gc();
        int[] startPermutation = new int[data.distance.length];
        for (int i = 0; i < data.distance.length; i++) {
            startPermutation[i] = i;
        }


        long startTime = System.nanoTime();
        int[] permutation = TwoOpt.twoOpt(data, startPermutation);
        long endTime = System.nanoTime();

        return new Result(endTime - startTime, Utils.routeLength(permutation, data));
    }

    public static Result runTwoOptWithStartPermutation(TSPData data, int[] startPermutation) {
        //System.gc();
        long startTime = System.nanoTime();
        int[] permutation = TwoOpt.twoOpt(data, startPermutation);
        long endTime = System.nanoTime();

        return new Result(endTime - startTime, Utils.routeLength(permutation, data));
    }

    public static Result runAcceleratedTwoOpt(TSPData data) {
        //System.gc();
        int[] startPermutation = new int[data.distance.length];
        for (int i = 0; i < data.distance.length; i++) {
            startPermutation[i] = i;
        }


        long startTime = System.nanoTime();
        int[] permutation = TwoOpt.acceleratedTwoOpt(data, startPermutation);
        long endTime = System.nanoTime();

        return new Result(endTime - startTime, Utils.routeLength(permutation, data));
    }

    public static Result runAcceleratedTwoOptWithStartPermutation(TSPData data, int[] startPermutation) {
        //System.gc();
        long startTime = System.nanoTime();
        int[] permutation = TwoOpt.acceleratedTwoOpt(data, startPermutation);
        long endTime = System.nanoTime();

        return new Result(endTime - startTime, Utils.routeLength(permutation, data));
    }

    public static Result runExtendedNeighborIntoTwoOpt(TSPData data){
        //System.gc();
        long startTime = System.nanoTime();
        int[] permutation = NeighborExtended.neighborExtended(data);
        permutation = TwoOpt.twoOpt(data, permutation);
        long endTime = System.nanoTime();

        return new Result(endTime - startTime, Utils.routeLength(permutation, data));
    }

    public static Result runExtendedNeighborIntoAcceleratedTwoOpt(TSPData data){
        //System.gc();
        long startTime = System.nanoTime();
        int[] permutation = NeighborExtended.neighborExtended(data);
        permutation = TwoOpt.acceleratedTwoOpt(data, permutation);
        long endTime = System.nanoTime();

        return new Result(endTime - startTime, Utils.routeLength(permutation, data));
    }

    //Basic Tabu
    public static Result runBasicSwapTabuSearchIterationStop(TSPData data, int tabuListSize, long iterationCount, boolean aspiration, long stagnationMaxIterationsWithoutImprovement) {
        int[] startPermutation = new int[data.distance.length];
        for (int i = 0; i < data.distance.length; i++) {
            startPermutation[i] = i;
        }

        long startTime = System.nanoTime();
        int[] permutation = Tabu.tabuSearch(new SwapBrowser(), new IterationStopCondition(iterationCount), startPermutation, data, tabuListSize, aspiration, stagnationMaxIterationsWithoutImprovement);
        long endTime = System.nanoTime();

        return new Result(endTime - startTime, Utils.routeLength(permutation, data));
    }
    public static Result runBasicSwapTabuSearchTimeStop(TSPData data, int tabuListSize, long runtime, boolean aspiration, long stagnationMaxIterationsWithoutImprovement) {
        int[] startPermutation = new int[data.distance.length];
        for (int i = 0; i < data.distance.length; i++) {
            startPermutation[i] = i;
        }

        long startTime = System.nanoTime();
        int[] permutation = Tabu.tabuSearch(new SwapBrowser(), new TimeStopCondition(runtime), startPermutation, data, tabuListSize, aspiration, stagnationMaxIterationsWithoutImprovement);
        long endTime = System.nanoTime();

        return new Result(endTime - startTime, Utils.routeLength(permutation, data));
    }
    public static Result runBasicInsertTabuSearchIterationStop(TSPData data, int tabuListSize, long iterationCount, boolean aspiration, long stagnationMaxIterationsWithoutImprovement) {
        int[] startPermutation = new int[data.distance.length];
        for (int i = 0; i < data.distance.length; i++) {
            startPermutation[i] = i;
        }

        long startTime = System.nanoTime();
        int[] permutation = Tabu.tabuSearch(new InsertBrowser(), new IterationStopCondition(iterationCount), startPermutation, data, tabuListSize, aspiration, stagnationMaxIterationsWithoutImprovement);
        long endTime = System.nanoTime();

        return new Result(endTime - startTime, Utils.routeLength(permutation, data));
    }
    public static Result runBasicInsertTabuSearchTimeStop(TSPData data, int tabuListSize, long runtime, boolean aspiration, long stagnationMaxIterationsWithoutImprovement) {
        int[] startPermutation = new int[data.distance.length];
        for (int i = 0; i < data.distance.length; i++) {
            startPermutation[i] = i;
        }

        long startTime = System.nanoTime();
        int[] permutation = Tabu.tabuSearch(new InsertBrowser(), new TimeStopCondition(runtime), startPermutation, data, tabuListSize, aspiration, stagnationMaxIterationsWithoutImprovement);
        long endTime = System.nanoTime();

        return new Result(endTime - startTime, Utils.routeLength(permutation, data));
    }
    public static Result runBasicInvertTabuSearchIterationStop(TSPData data, int tabuListSize, long iterationCount, boolean aspiration, long stagnationMaxIterationsWithoutImprovement) {
        int[] startPermutation = new int[data.distance.length];
        for (int i = 0; i < data.distance.length; i++) {
            startPermutation[i] = i;
        }

        long startTime = System.nanoTime();
        int[] permutation = Tabu.tabuSearch(new InvertBrowser(), new IterationStopCondition(iterationCount), startPermutation, data, tabuListSize, aspiration, stagnationMaxIterationsWithoutImprovement);
        long endTime = System.nanoTime();

        return new Result(endTime - startTime, Utils.routeLength(permutation, data));
    }
    public static Result runBasicInvertTabuSearchTimeStop(TSPData data, int tabuListSize, long runtime, boolean aspiration, long stagnationMaxIterationsWithoutImprovement) {
        int[] startPermutation = new int[data.distance.length];
        for (int i = 0; i < data.distance.length; i++) {
            startPermutation[i] = i;
        }

        long startTime = System.nanoTime();
        int[] permutation = Tabu.tabuSearch(new InvertBrowser(), new TimeStopCondition(runtime), startPermutation, data, tabuListSize, aspiration, stagnationMaxIterationsWithoutImprovement);
        long endTime = System.nanoTime();

        return new Result(endTime - startTime, Utils.routeLength(permutation, data));
    }

    //Accelerated Tabu
    public static Result runAcceleratedSwapTabuSearchIterationStop(TSPData data, int tabuListSize, long iterationCount, boolean aspiration, long stagnationMaxIterationsWithoutImprovement) {
        int[] startPermutation = new int[data.distance.length];
        for (int i = 0; i < data.distance.length; i++) {
            startPermutation[i] = i;
        }

        long startTime = System.nanoTime();
        int[] permutation = Tabu.tabuSearch(new AcceleratedSwapBrowser(), new IterationStopCondition(iterationCount), startPermutation, data, tabuListSize, aspiration, stagnationMaxIterationsWithoutImprovement);
        long endTime = System.nanoTime();

        return new Result(endTime - startTime, Utils.routeLength(permutation, data));
    }
    public static Result runAcceleratedSwapTabuSearchTimeStop(TSPData data, int tabuListSize, long runtime, boolean aspiration, long stagnationMaxIterationsWithoutImprovement) {
        int[] startPermutation = new int[data.distance.length];
        for (int i = 0; i < data.distance.length; i++) {
            startPermutation[i] = i;
        }

        long startTime = System.nanoTime();
        int[] permutation = Tabu.tabuSearch(new AcceleratedSwapBrowser(), new TimeStopCondition(runtime), startPermutation, data, tabuListSize, aspiration, stagnationMaxIterationsWithoutImprovement);
        long endTime = System.nanoTime();

        return new Result(endTime - startTime, Utils.routeLength(permutation, data));
    }
    public static Result runAcceleratedInsertTabuSearchIterationStop(TSPData data, int tabuListSize, long iterationCount, boolean aspiration, long stagnationMaxIterationsWithoutImprovement) {
        int[] startPermutation = new int[data.distance.length];
        for (int i = 0; i < data.distance.length; i++) {
            startPermutation[i] = i;
        }

        long startTime = System.nanoTime();
        int[] permutation = Tabu.tabuSearch(new AcceleratedInsertBrowser(), new IterationStopCondition(iterationCount), startPermutation, data, tabuListSize, aspiration, stagnationMaxIterationsWithoutImprovement);
        long endTime = System.nanoTime();

        return new Result(endTime - startTime, Utils.routeLength(permutation, data));
    }
    public static Result runAcceleratedInsertTabuSearchTimeStop(TSPData data, int tabuListSize, long runtime, boolean aspiration, long stagnationMaxIterationsWithoutImprovement) {
        int[] startPermutation = new int[data.distance.length];
        for (int i = 0; i < data.distance.length; i++) {
            startPermutation[i] = i;
        }

        long startTime = System.nanoTime();
        int[] permutation = Tabu.tabuSearch(new AcceleratedInsertBrowser(), new TimeStopCondition(runtime), startPermutation, data, tabuListSize, aspiration, stagnationMaxIterationsWithoutImprovement);
        long endTime = System.nanoTime();

        return new Result(endTime - startTime, Utils.routeLength(permutation, data));
    }
    public static Result runAcceleratedInvertTabuSearchIterationStop(TSPData data, int tabuListSize, long iterationCount, boolean aspiration, long stagnationMaxIterationsWithoutImprovement) {
        int[] startPermutation = new int[data.distance.length];
        for (int i = 0; i < data.distance.length; i++) {
            startPermutation[i] = i;
        }

        long startTime = System.nanoTime();
        int[] permutation = Tabu.tabuSearch(new AcceleratedInvertBrowser(), new IterationStopCondition(iterationCount), startPermutation, data, tabuListSize, aspiration, stagnationMaxIterationsWithoutImprovement);
        long endTime = System.nanoTime();

        return new Result(endTime - startTime, Utils.routeLength(permutation, data));
    }
    public static Result runAcceleratedInvertTabuSearchTimeStop(TSPData data, int tabuListSize, long runtime, boolean aspiration, long stagnationMaxIterationsWithoutImprovement) {
        int[] startPermutation = new int[data.distance.length];
        for (int i = 0; i < data.distance.length; i++) {
            startPermutation[i] = i;
        }

        long startTime = System.nanoTime();
        int[] permutation = Tabu.tabuSearch(new AcceleratedInvertBrowser(), new TimeStopCondition(runtime), startPermutation, data, tabuListSize, aspiration, stagnationMaxIterationsWithoutImprovement);
        long endTime = System.nanoTime();

        return new Result(endTime - startTime, Utils.routeLength(permutation, data));
    }

    //Multithreaded Basic Tabu
    public static Result runMultithreadedBasicSwapTabuSearchIterationStop(TSPData data, int tabuListSize, long iterationCount, boolean aspiration, long stagnationMaxIterationsWithoutImprovement) {
        int[] startPermutation = new int[data.distance.length];
        for (int i = 0; i < data.distance.length; i++) {
            startPermutation[i] = i;
        }

        long startTime = System.nanoTime();
        int[] permutation = Tabu.tabuSearch(new SwapMultithreadedBrowser(), new IterationStopCondition(iterationCount), startPermutation, data, tabuListSize, aspiration, stagnationMaxIterationsWithoutImprovement);
        long endTime = System.nanoTime();

        return new Result(endTime - startTime, Utils.routeLength(permutation, data));
    }
    public static Result runMultithreadedBasicSwapTabuSearchTimeStop(TSPData data, int tabuListSize, long runtime, boolean aspiration, long stagnationMaxIterationsWithoutImprovement) {
        int[] startPermutation = new int[data.distance.length];
        for (int i = 0; i < data.distance.length; i++) {
            startPermutation[i] = i;
        }

        long startTime = System.nanoTime();
        int[] permutation = Tabu.tabuSearch(new SwapMultithreadedBrowser(), new TimeStopCondition(runtime), startPermutation, data, tabuListSize, aspiration, stagnationMaxIterationsWithoutImprovement);
        long endTime = System.nanoTime();

        return new Result(endTime - startTime, Utils.routeLength(permutation, data));
    }
    public static Result runMultithreadedBasicInsertTabuSearchIterationStop(TSPData data, int tabuListSize, long iterationCount, boolean aspiration, long stagnationMaxIterationsWithoutImprovement) {
        int[] startPermutation = new int[data.distance.length];
        for (int i = 0; i < data.distance.length; i++) {
            startPermutation[i] = i;
        }

        long startTime = System.nanoTime();
        int[] permutation = Tabu.tabuSearch(new InsertMultithreadedBrowser(), new IterationStopCondition(iterationCount), startPermutation, data, tabuListSize, aspiration, stagnationMaxIterationsWithoutImprovement);
        long endTime = System.nanoTime();

        return new Result(endTime - startTime, Utils.routeLength(permutation, data));
    }
    public static Result runMultithreadedBasicInsertTabuSearchTimeStop(TSPData data, int tabuListSize, long runtime, boolean aspiration, long stagnationMaxIterationsWithoutImprovement) {
        int[] startPermutation = new int[data.distance.length];
        for (int i = 0; i < data.distance.length; i++) {
            startPermutation[i] = i;
        }

        long startTime = System.nanoTime();
        int[] permutation = Tabu.tabuSearch(new InsertMultithreadedBrowser(), new TimeStopCondition(runtime), startPermutation, data, tabuListSize, aspiration, stagnationMaxIterationsWithoutImprovement);
        long endTime = System.nanoTime();

        return new Result(endTime - startTime, Utils.routeLength(permutation, data));
    }
    public static Result runMultithreadedBasicInvertTabuSearchIterationStop(TSPData data, int tabuListSize, long iterationCount, boolean aspiration, long stagnationMaxIterationsWithoutImprovement) {
        int[] startPermutation = new int[data.distance.length];
        for (int i = 0; i < data.distance.length; i++) {
            startPermutation[i] = i;
        }

        long startTime = System.nanoTime();
        int[] permutation = Tabu.tabuSearch(new InvertMultithreadedBrowser(), new IterationStopCondition(iterationCount), startPermutation, data, tabuListSize, aspiration, stagnationMaxIterationsWithoutImprovement);
        long endTime = System.nanoTime();

        return new Result(endTime - startTime, Utils.routeLength(permutation, data));
    }
    public static Result runMultithreadedBasicInvertTabuSearchTimeStop(TSPData data, int tabuListSize, long runtime, boolean aspiration, long stagnationMaxIterationsWithoutImprovement) {
        int[] startPermutation = new int[data.distance.length];
        for (int i = 0; i < data.distance.length; i++) {
            startPermutation[i] = i;
        }

        long startTime = System.nanoTime();
        int[] permutation = Tabu.tabuSearch(new InvertMultithreadedBrowser(), new TimeStopCondition(runtime), startPermutation, data, tabuListSize, aspiration, stagnationMaxIterationsWithoutImprovement);
        long endTime = System.nanoTime();

        return new Result(endTime - startTime, Utils.routeLength(permutation, data));
    }

    //Multithreaded Accelerated Tabu
    public static Result runMultithreadedAcceleratedSwapTabuSearchIterationStop(TSPData data, int tabuListSize, long iterationCount, boolean aspiration, long stagnationMaxIterationsWithoutImprovement) {
        int[] startPermutation = new int[data.distance.length];
        for (int i = 0; i < data.distance.length; i++) {
            startPermutation[i] = i;
        }

        long startTime = System.nanoTime();
        int[] permutation = Tabu.tabuSearch(new AcceleratedSwapMultithreadedBrowser(), new IterationStopCondition(iterationCount), startPermutation, data, tabuListSize, aspiration, stagnationMaxIterationsWithoutImprovement);
        long endTime = System.nanoTime();

        return new Result(endTime - startTime, Utils.routeLength(permutation, data));
    }
    public static Result runMultithreadedAcceleratedSwapTabuSearchTimeStop(TSPData data, int tabuListSize, long runtime, boolean aspiration, long stagnationMaxIterationsWithoutImprovement) {
        int[] startPermutation = new int[data.distance.length];
        for (int i = 0; i < data.distance.length; i++) {
            startPermutation[i] = i;
        }

        long startTime = System.nanoTime();
        int[] permutation = Tabu.tabuSearch(new AcceleratedSwapMultithreadedBrowser(), new TimeStopCondition(runtime), startPermutation, data, tabuListSize, aspiration, stagnationMaxIterationsWithoutImprovement);
        long endTime = System.nanoTime();

        return new Result(endTime - startTime, Utils.routeLength(permutation, data));
    }
    public static Result runMultithreadedAcceleratedInsertTabuSearchIterationStop(TSPData data, int tabuListSize, long iterationCount, boolean aspiration, long stagnationMaxIterationsWithoutImprovement) {
        int[] startPermutation = new int[data.distance.length];
        for (int i = 0; i < data.distance.length; i++) {
            startPermutation[i] = i;
        }

        long startTime = System.nanoTime();
        int[] permutation = Tabu.tabuSearch(new AcceleratedInsertMultithreadedBrowser(), new IterationStopCondition(iterationCount), startPermutation, data, tabuListSize, aspiration, stagnationMaxIterationsWithoutImprovement);
        long endTime = System.nanoTime();

        return new Result(endTime - startTime, Utils.routeLength(permutation, data));
    }
    public static Result runMultithreadedAcceleratedInsertTabuSearchTimeStop(TSPData data, int tabuListSize, long runtime, boolean aspiration, long stagnationMaxIterationsWithoutImprovement) {
        int[] startPermutation = new int[data.distance.length];
        for (int i = 0; i < data.distance.length; i++) {
            startPermutation[i] = i;
        }

        long startTime = System.nanoTime();
        int[] permutation = Tabu.tabuSearch(new AcceleratedInsertMultithreadedBrowser(), new TimeStopCondition(runtime), startPermutation, data, tabuListSize, aspiration, stagnationMaxIterationsWithoutImprovement);
        long endTime = System.nanoTime();

        return new Result(endTime - startTime, Utils.routeLength(permutation, data));
    }
    public static Result runMultithreadedAcceleratedInvertTabuSearchIterationStop(TSPData data, int tabuListSize, long iterationCount, boolean aspiration, long stagnationMaxIterationsWithoutImprovement) {
        int[] startPermutation = new int[data.distance.length];
        for (int i = 0; i < data.distance.length; i++) {
            startPermutation[i] = i;
        }

        long startTime = System.nanoTime();
        int[] permutation = Tabu.tabuSearch(new AcceleratedInvertMultithreadedBrowser(), new IterationStopCondition(iterationCount), startPermutation, data, tabuListSize, aspiration, stagnationMaxIterationsWithoutImprovement);
        long endTime = System.nanoTime();

        return new Result(endTime - startTime, Utils.routeLength(permutation, data));
    }
    public static Result runMultithreadedAcceleratedInvertTabuSearchTimeStop(TSPData data, int tabuListSize, long runtime, boolean aspiration, long stagnationMaxIterationsWithoutImprovement) {
        int[] startPermutation = new int[data.distance.length];
        for (int i = 0; i < data.distance.length; i++) {
            startPermutation[i] = i;
        }

        long startTime = System.nanoTime();
        int[] permutation = Tabu.tabuSearch(new AcceleratedInvertMultithreadedBrowser(), new TimeStopCondition(runtime), startPermutation, data, tabuListSize, aspiration, stagnationMaxIterationsWithoutImprovement);
        long endTime = System.nanoTime();

        return new Result(endTime - startTime, Utils.routeLength(permutation, data));
    }




    public static class Result{
        public long time;
        public double out;
        public Result(long time, double out){
            this.time = time;
            this.out = out;
        }
    }
}
