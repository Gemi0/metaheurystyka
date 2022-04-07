package benchmark;

import main.TSPData;
import main.TSPGenerator;

import java.util.ArrayList;

public abstract class RandomRepeatingBenchmark extends RepeatingBenchmark {
    protected double minDistance;
    protected double maxDistance;
    protected boolean symetric;

    final protected ArrayList<Benchmark.Result> singlePass(int n) {
        TSPData problem = TSPGenerator.generateWithoutCoords(minDistance, maxDistance, n, symetric);
        return singlePassWithData(problem);
    }

    protected abstract ArrayList<Benchmark.Result> singlePassWithData(TSPData problem);

}
