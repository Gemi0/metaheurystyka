package benchmark.tests.list1.random;

import benchmark.Benchmark;
import benchmark.RandomRepeatingBenchmark;
import main.TSPData;

import java.util.ArrayList;
import java.util.Arrays;

public class KRandomSymetric extends RandomRepeatingBenchmark {

    public KRandomSymetric() {
        minDistance = 1;
        maxDistance = 10000;
        symetric = true;
        minN = 10;
        maxN = 250;
        step = 10;
        repeats = 50;
        names = new ArrayList<>(Arrays.asList("kRandom-100", "kRandom-1000", "kRandom-10000", "kRandom-100000"));

    }

    @Override
    protected ArrayList<Benchmark.Result> singlePassWithData(TSPData problem) {
        System.gc();
        ArrayList<Benchmark.Result> results = new ArrayList<>();
        results.add(Benchmark.runKRandom(problem, 100));
        results.add(Benchmark.runKRandom(problem, 1000));
        results.add(Benchmark.runKRandom(problem, 10000));
        results.add(Benchmark.runKRandom(problem, 100000));
        return results;
    }

    public static void main(String[] args) {
        KRandomSymetric benchmark = new KRandomSymetric();
        benchmark.runBenchmark();
        benchmark.saveResults("C:\\Users\\Admin\\Documents\\bench\\RandomTests\\KRandomTest\\Symetric");
    }
}
