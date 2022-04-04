package benchmark;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;

abstract public class TSPBenchmark {

    protected int repeats;
    protected ArrayList<String> names;
    protected ArrayList<Benchmark.Result> benchmarkResults;
    protected double bestSolution;
    protected String problemName;

    public void runBenchmark() {
        benchmarkResults = new ArrayList<>(names.size());
        for (int i = 0; i < names.size(); i++) {
            benchmarkResults.add(new Benchmark.Result(0, 0));
        }

        for (int repeat = 0; repeat < repeats; repeat++) {
            ArrayList<Benchmark.Result> results = singlePass();

            for (Benchmark.Result result : results) {
                result.out = (result.out - bestSolution) / bestSolution;
            }

            for (int i = 0; i < results.size(); i++) {
                benchmarkResults.get(i).out += results.get(i).out;
                benchmarkResults.get(i).time += results.get(i).time;
            }
        }

        for (Benchmark.Result result : benchmarkResults) {
            result.out /= repeats;
            result.time /= repeats;
        }
    }

    public void saveResults(String saveDirectoryPath) {
        File file = new File(saveDirectoryPath + File.separator + problemName + ".csv");
        try (PrintWriter pw = new PrintWriter((new FileOutputStream(file)))) {
            for (int i = 0; i < names.size(); i++) {
                String name = names.get(i);
                pw.println(name + ";" + benchmarkResults.get(i).time + ";" + benchmarkResults.get(i).out);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected abstract ArrayList<Benchmark.Result> singlePass();
}
