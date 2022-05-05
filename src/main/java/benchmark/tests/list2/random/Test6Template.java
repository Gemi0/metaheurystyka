package benchmark.tests.list2.random;

import benchmark.Benchmark;
import benchmark.RandomRepeatingBenchmark;
import main.TSPData;

import java.util.ArrayList;
import java.util.Arrays;

public class Test6Template extends RandomRepeatingBenchmark {

    public Test6Template() {
        minDistance = 1;
        maxDistance = 10000;
        symetric = true;
        minN = 10;
        maxN = 200;
        step = 10;
        repeats = 10;
        names = new ArrayList<>(Arrays.asList("yes-stagnation-no-aspiration", "no-stagnation-no-aspiration", "yes-stagnation-yes-aspiration", "no-stagnation-yes-aspiration"));

    }

    @Override
    protected ArrayList<Benchmark.Result> singlePassWithData(TSPData problem) {
        System.gc();
        ArrayList<Benchmark.Result> results = new ArrayList<>();
        results.add(Benchmark.runMultithreadedAcceleratedInvertTabuSearchIterationStop(problem, 7, 10000, false, 100));
        results.add(Benchmark.runMultithreadedAcceleratedInvertTabuSearchIterationStop(problem, 7, 10000, false, Long.MAX_VALUE));
        results.add(Benchmark.runMultithreadedAcceleratedInvertTabuSearchIterationStop(problem, 7, 10000, true, 100));
        results.add(Benchmark.runMultithreadedAcceleratedInvertTabuSearchIterationStop(problem, 7, 10000, true, Long.MAX_VALUE));
        return results;
    }

    public static void main(String[] args) {
        Test6Template benchmark = new Test6Template();
        benchmark.runBenchmark();
        benchmark.saveResults("C:\\Users\\Admin\\Documents\\bench\\");
    }
}