package benchmark.tests.list1.tsp;

import benchmark.Benchmark;
import benchmark.TSPBenchmark;
import main.TSPData;
import main.XMLParser;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public class AsymetricTSPBenchmark extends TSPBenchmark {

    protected TSPData problem;

    public AsymetricTSPBenchmark(String problemName, File problemFile, double bestSolution) {
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
        ArrayList<String> problems = new ArrayList<>(Arrays.asList("ftv33", "ftv35", "ftv38", "ftv44", "ftv47", "ftv170"));
        ArrayList<Double> bestSolutions = new ArrayList<>(Arrays.asList(1286.0, 1473.0, 1530.0, 1613.0, 1776.0, 2755.0));
        for(int i = 0; i < problems.size() ; i++) {
            String problemName = problems.get(i);
            double bestSolution = bestSolutions.get(i);
            AsymetricTSPBenchmark benchmark = new AsymetricTSPBenchmark(problemName, new File(System.getProperty("user.home") + "\\IdeaProjects\\metaheurystyka\\src\\main\\java\\data\\asymetric\\" + problemName + ".xml"), bestSolution);
            benchmark.runBenchmark();
            benchmark.saveResults("C:\\Users\\Admin\\Documents\\bench\\TSPBenchmark\\Asymetric");
        }
    }
}
