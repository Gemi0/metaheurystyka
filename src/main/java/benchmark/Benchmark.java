package benchmark;

import algorithms.*;
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


    public static class Result{
        public long time;
        public double out;
        public Result(long time, double out){
            this.time = time;
            this.out = out;
        }
    }
}