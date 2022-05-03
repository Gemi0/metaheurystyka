package benchmark;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;

public abstract class RepeatingBenchmark {
    protected int minN;
    protected int maxN;
    protected int step;
    protected int repeats;
    protected ArrayList<String> names;
    protected ArrayList<ArrayList<Benchmark.Result>> benchmarkResults;


    public void runBenchmark() {
        ArrayList<ArrayList<Benchmark.Result>> total = new ArrayList<>();
        for (int n = minN; n <= maxN; n += step) {
            System.out.println("Currently processing: " + n + "/" + maxN);

            ArrayList<Benchmark.Result> totalForN = new ArrayList<>(names.size());
            for (int i = 0; i < names.size(); i++) {
                totalForN.add(new Benchmark.Result(0, 0));
            }

            for (int repeat = 0; repeat < repeats; repeat++) {
                System.out.println(repeat);//TEMPORARY
                ArrayList<Benchmark.Result> results = singlePass(n);

                double min = Double.MAX_VALUE;
                for (Benchmark.Result result : results) {
                    min = Math.min(min, result.out);
                }

                for (Benchmark.Result result : results) {
                    result.out = (result.out - min) / min;
                }

                for (int i = 0; i < results.size(); i++) {
                    totalForN.get(i).out += results.get(i).out;
                    totalForN.get(i).time += results.get(i).time;
                }
            }

            for (Benchmark.Result result : totalForN) {
                result.out /= repeats;
                result.time /= repeats;
            }

            total.add(totalForN);
        }
        benchmarkResults = total;
    }

    public void saveResults(String saveDirectoryPath) {
        for (int i = 0; i < names.size(); i++) {
            int n = minN;
            String name = names.get(i);
            File file = new File(saveDirectoryPath + File.separator + name + ".csv");
            try (PrintWriter pw = new PrintWriter((new FileOutputStream(file)))) {
                for (ArrayList<Benchmark.Result> totalForN : benchmarkResults) {
                    pw.println(n + ";" + totalForN.get(i).time + ";" + totalForN.get(i).out);
                    n += step;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    protected abstract ArrayList<Benchmark.Result> singlePass(int n);
}
