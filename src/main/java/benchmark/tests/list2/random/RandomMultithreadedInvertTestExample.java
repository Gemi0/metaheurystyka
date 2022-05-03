package benchmark.tests.list2.random;

import benchmark.Benchmark;
import benchmark.RandomRepeatingBenchmark;
import main.TSPData;

import java.util.ArrayList;
import java.util.Arrays;

public class RandomMultithreadedInvertTestExample extends RandomRepeatingBenchmark {

    public RandomMultithreadedInvertTestExample() {
        minDistance = 1;
        maxDistance = 10000;
        symetric = true;
        minN = 10;
        maxN = 100;
        step = 10;
        repeats = 20;
        names = new ArrayList<>(Arrays.asList("yes-stagnation-no-aspiration", "no-stagnation-no-aspiration", "yes-stagnation-yes-aspiration", "no-stagnation-yes-aspiration"));

    }

    @Override
    protected ArrayList<Benchmark.Result> singlePassWithData(TSPData problem) {
        System.gc();
        ArrayList<Benchmark.Result> results = new ArrayList<>();
        results.add(Benchmark.runMultithreadedAcceleratedInvertTabuSearchIterationStop(problem, 20, 5000, false, 200));
        results.add(Benchmark.runMultithreadedAcceleratedInvertTabuSearchIterationStop(problem, 20, 5000, false, Long.MAX_VALUE));
        results.add(Benchmark.runMultithreadedAcceleratedInvertTabuSearchIterationStop(problem, 20, 5000, true, 200));
        results.add(Benchmark.runMultithreadedAcceleratedInvertTabuSearchIterationStop(problem, 20, 5000, true, Long.MAX_VALUE));
        return results;
    }

    public static void main(String[] args) {
        RandomMultithreadedInvertTestExample benchmark = new RandomMultithreadedInvertTestExample();
        benchmark.runBenchmark();
        benchmark.saveResults("C:\\Users\\Admin\\Documents\\bench\\");
    }
}
