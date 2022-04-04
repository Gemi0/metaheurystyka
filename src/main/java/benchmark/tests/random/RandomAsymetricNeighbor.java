package benchmark.tests.random;

import benchmark.Benchmark;
import benchmark.RandomRepeatingBenchmark;
import main.TSPData;

import java.util.ArrayList;
import java.util.Arrays;

public class RandomAsymetricNeighbor extends RandomRepeatingBenchmark {

    public RandomAsymetricNeighbor() {
        minDistance = 1;
        maxDistance = 10000;
        symetric = false;
        minN = 10;
        maxN = 1500;
        step = 10;
        repeats = 50;
        names = new ArrayList<>(Arrays.asList("neighbor"));

    }

    @Override
    protected ArrayList<Benchmark.Result> singlePassWithData(TSPData problem) {
        System.gc();
        ArrayList<Benchmark.Result> results = new ArrayList<>();
        results.add(Benchmark.runNeighbor(problem, 0));
        return results;
    }

    public static void main(String[] args) {
        RandomAsymetricNeighbor benchmark = new RandomAsymetricNeighbor();
        benchmark.runBenchmark();
        benchmark.saveResults("C:\\Users\\Admin\\Documents\\bench");
    }
}
