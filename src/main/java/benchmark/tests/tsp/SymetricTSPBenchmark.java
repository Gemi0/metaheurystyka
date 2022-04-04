package benchmark.tests.tsp;

import benchmark.Benchmark;

import benchmark.TSPBenchmark;
import main.TSPData;
import main.XMLParser;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public class SymetricTSPBenchmark extends TSPBenchmark {

    protected TSPData problem;

    public SymetricTSPBenchmark(String problemName, File problemFile, double bestSolution) {
        repeats = 10;
        names = new ArrayList<>(Arrays.asList("kRandom-10000", "neighbor", "neighborExtended", "twoOpt", "twoOptExtended"));
        problem = new TSPData(XMLParser.parseFile(problemFile));
        this.problemName = problemName;
        this.bestSolution = bestSolution;
    }

    @Override
    protected ArrayList<Benchmark.Result> singlePass() {
        System.gc();
        ArrayList<Benchmark.Result> results = new ArrayList<>();
        results.add(Benchmark.runKRandom(problem, 10000));
        results.add(Benchmark.runNeighbor(problem, 0));
        results.add(Benchmark.runNeighborExtended(problem));
        results.add(Benchmark.runTwoOpt(problem));
        results.add(Benchmark.runExtendedNeighborIntoTwoOpt(problem));
        return results;
    }

    public static void main(String[] args) {
        ArrayList<String> problems = new ArrayList<>(Arrays.asList("a280", "att48", "berlin52", "brazil58", "brg180", "burma14"));
        ArrayList<Double> bestSolutions = new ArrayList<>(Arrays.asList(2579.0, 10628.0, 7542.0, 25395.0, 1950.0, 3323.0));
        for(int i = 0; i < problems.size() ; i++) {
            String problemName = problems.get(i);
            double bestSolution = bestSolutions.get(i);
            SymetricTSPBenchmark benchmark = new SymetricTSPBenchmark(problemName, new File(System.getProperty("user.home") + "\\IdeaProjects\\metaheurystyka\\src\\main\\java\\data\\symetric\\" + problemName + ".xml"), bestSolution);
            benchmark.runBenchmark();
            benchmark.saveResults("C:\\Users\\Admin\\Documents\\bench\\TSPBenchmark\\Symetric");
        }
    }
}
