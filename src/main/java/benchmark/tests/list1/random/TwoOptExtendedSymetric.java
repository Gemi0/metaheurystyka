package benchmark.tests.list1.random;

import benchmark.Benchmark;
import benchmark.RandomRepeatingBenchmark;
import main.TSPData;

import java.util.ArrayList;
import java.util.Arrays;

public class TwoOptExtendedSymetric extends RandomRepeatingBenchmark {

    public TwoOptExtendedSymetric() {
        minDistance = 1;
        maxDistance = 10000;
        symetric = true;
        minN = 10;
        maxN = 250;
        step = 10;
        repeats = 10;
        names = new ArrayList<>(Arrays.asList("neighborExtended", "twoOpt", "twoOptExtended"));

    }

    @Override
    protected ArrayList<Benchmark.Result> singlePassWithData(TSPData problem) {
        System.gc();
        ArrayList<Benchmark.Result> results = new ArrayList<>();
        results.add(Benchmark.runNeighborExtended(problem));
        results.add(Benchmark.runTwoOpt(problem));
        results.add(Benchmark.runExtendedNeighborIntoTwoOpt(problem));
        return results;
    }

    public static void main(String[] args) {
        TwoOptExtendedSymetric benchmark = new TwoOptExtendedSymetric();
        benchmark.runBenchmark();
        benchmark.saveResults("C:\\Users\\Admin\\Documents\\bench\\RandomTests\\TwoOptvsExtendedNeighborvsBoth\\Symetric");
    }
}
