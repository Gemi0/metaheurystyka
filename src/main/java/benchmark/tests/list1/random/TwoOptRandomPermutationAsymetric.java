package benchmark.tests.list1.random;

import algorithms.Utils;
import benchmark.Benchmark;
import benchmark.RandomRepeatingBenchmark;
import main.TSPData;

import java.util.ArrayList;
import java.util.Arrays;

public class TwoOptRandomPermutationAsymetric extends RandomRepeatingBenchmark {

    public TwoOptRandomPermutationAsymetric() {
        minDistance = 1;
        maxDistance = 10000;
        symetric = false;
        minN = 10;
        maxN = 250;
        step = 10;
        repeats = 10;
        names = new ArrayList<>(Arrays.asList("twoOpt1", "twoOpt-1", "twoOptRand"));

    }

    @Override
    protected ArrayList<Benchmark.Result> singlePassWithData(TSPData problem) {
        System.gc();
        ArrayList<Benchmark.Result> results = new ArrayList<>();
        results.add(Benchmark.runTwoOpt(problem));
        int[] permutation = new int[problem.distance.length];
        for(int i = permutation.length - 1; i >= 0; i--) {
            permutation[i] = i;
        }
        results.add(Benchmark.runTwoOptWithStartPermutation(problem, permutation));
        Utils.shuffle(permutation);
        results.add(Benchmark.runTwoOptWithStartPermutation(problem, permutation));
        return results;
    }

    public static void main(String[] args) {
        TwoOptRandomPermutationAsymetric benchmark = new TwoOptRandomPermutationAsymetric();
        benchmark.runBenchmark();
        benchmark.saveResults("C:\\Users\\Admin\\Documents\\bench\\RandomTests\\TwoOptRandomPermutation\\Asymetric");
    }
}
