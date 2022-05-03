package benchmark.tests.list2.random;

import benchmark.Benchmark;
import benchmark.RandomRepeatingBenchmark;
import main.TSPData;

import java.util.ArrayList;
import java.util.Arrays;

public class RandomMultithreadedInvertTest extends RandomRepeatingBenchmark {

    public RandomMultithreadedInvertTest() {
        minDistance = 1;
        maxDistance = 10000;
        symetric = true;
        minN = 1000;
        maxN = 1000;
        step = 10;
        repeats = 5;
        names = new ArrayList<>(Arrays.asList("single", "multi", "multi-acc", "acc"));

    }

    @Override
    protected ArrayList<Benchmark.Result> singlePassWithData(TSPData problem) {
        System.gc();
        /*ArrayList<Benchmark.Result> results = new ArrayList<>();
        results.add(Benchmark.runBasicInvertTabuSearch(problem, 500, 3000000000L));
        results.add(Benchmark.runInvertMultithreadedTabuSearch(problem, 500, 3000000000L));
        results.add(Benchmark.runAcceleratedInvertMultithreadedTabuSearch(problem, 500, 3000000000L));
        results.add(Benchmark.runAccInvertTabuSearch(problem, 500, 3000000000L));
        return results;*/
        return null;
    }

    public static void main(String[] args) {
        RandomMultithreadedInvertTest benchmark = new RandomMultithreadedInvertTest();
        benchmark.runBenchmark();
        benchmark.saveResults("C:\\Users\\Admin\\Documents\\bench\\");
    }
}
