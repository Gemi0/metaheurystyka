package benchmark.tests.list2.random;

import benchmark.Benchmark;
import benchmark.RandomRepeatingBenchmark;
import main.TSPData;

import java.util.ArrayList;
import java.util.Arrays;

public class Test2Template extends RandomRepeatingBenchmark {

    public Test2Template() {
        minDistance = 1;
        maxDistance = 10000;
        symetric = true;
        minN = 10;
        maxN = 500;
        step = 10;
        repeats = 10;
        names = new ArrayList<>(Arrays.asList("2n", "n", "n2", "n3", "n4", "n5", "n10", "7"));

    }

    @Override
    protected ArrayList<Benchmark.Result> singlePassWithData(TSPData problem) {
        System.gc();
        ArrayList<Benchmark.Result> results = new ArrayList<>();
        results.add(Benchmark.runMultithreadedAcceleratedSwapTabuSearchIterationStop(problem, problem.distance.length * 2, 5000, false, Long.MAX_VALUE));
        results.add(Benchmark.runMultithreadedAcceleratedSwapTabuSearchIterationStop(problem, problem.distance.length, 5000, false, Long.MAX_VALUE));
        results.add(Benchmark.runMultithreadedAcceleratedSwapTabuSearchIterationStop(problem, problem.distance.length/2, 5000, false, Long.MAX_VALUE));
        results.add(Benchmark.runMultithreadedAcceleratedSwapTabuSearchIterationStop(problem, problem.distance.length/3, 5000, false, Long.MAX_VALUE));
        results.add(Benchmark.runMultithreadedAcceleratedSwapTabuSearchIterationStop(problem, problem.distance.length/4, 5000, false, Long.MAX_VALUE));
        results.add(Benchmark.runMultithreadedAcceleratedSwapTabuSearchIterationStop(problem, problem.distance.length/5, 5000, false, Long.MAX_VALUE));
        results.add(Benchmark.runMultithreadedAcceleratedSwapTabuSearchIterationStop(problem, problem.distance.length/10, 5000, false, Long.MAX_VALUE));
        results.add(Benchmark.runMultithreadedAcceleratedSwapTabuSearchIterationStop(problem, 7, 5000, false, Long.MAX_VALUE));
        return results;
    }

    public static void main(String[] args) {
        Test2Template benchmark = new Test2Template();
        benchmark.runBenchmark();
        benchmark.saveResults("C:\\Users\\Admin\\Documents\\bench\\");
    }
}
