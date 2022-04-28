package benchmark.tests.list1.random;

import benchmark.Benchmark;
import benchmark.RandomRepeatingBenchmark;
import main.TSPData;

import java.util.ArrayList;
import java.util.Arrays;

public class RandomAsymetricAllAlgorithms extends RandomRepeatingBenchmark {

    public RandomAsymetricAllAlgorithms() {
        minDistance = 1;
        maxDistance = 10000;
        symetric = false;
        minN = 10;
        maxN = 250;
        step = 10;
        repeats = 50;
        names = new ArrayList<>(Arrays.asList("kRandom-10000", "neighbor", "neighborExtended", "twoOpt"));

    }

    @Override
    protected ArrayList<Benchmark.Result> singlePassWithData(TSPData problem) {
        System.gc();
        ArrayList<Benchmark.Result> results = new ArrayList<>();
        results.add(Benchmark.runKRandom(problem, 10000));
        results.add(Benchmark.runNeighbor(problem, 0));
        results.add(Benchmark.runNeighborExtended(problem));
        results.add(Benchmark.runTwoOpt(problem));
        return results;
    }

    public static void main(String[] args) {
        RandomAsymetricAllAlgorithms benchmark = new RandomAsymetricAllAlgorithms();
        benchmark.runBenchmark();
        benchmark.saveResults("C:\\Users\\Admin\\Documents\\bench\\RandomTests\\Asymetric");
    }
}
