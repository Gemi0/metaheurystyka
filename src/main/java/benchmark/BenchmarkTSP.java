package benchmark;

import algorithms.KRandom;
import algorithms.Neighbour;
import algorithms.NeighbourExtended;
import algorithms.TwoOpt;
import main.TSPData;
import main.TSPGenerator;
import main.Tour;
import main.XMLParser;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;

import static benchmark.Benchmark.Result;

public class BenchmarkTSP {

    public static final int ALG_NUM = 4;
    public static final int RPT = 10;
    public static final int K = 10000;

    public static final double bestKnownSol = 2755;
    public static final File file = new File("C:\\Users\\Admin\\Desktop\\out\\TSPLIB Tests\\AsymetricData\\ftv170.xml");

    public static void main(String[] args) {
        long startTime = 0;
        long endTime = 0;

        ArrayList<Benchmark.Result> totals = new ArrayList<>();
        for(int i = 0; i < ALG_NUM; i++){
            totals.add(new Benchmark.Result(0, 0));
        }
        for(int attempt = 0; attempt < RPT; attempt++) {
            ArrayList<Benchmark.Result> list = new ArrayList<>();
            TSPData problem = new TSPData(XMLParser.parseFile(file));//LOad

            startTime = System.nanoTime();
                Tour tour = KRandom.kRandom(problem, K);
                endTime = System.nanoTime() - startTime;
                list.add(new Result(endTime, tour.length(problem)));

                //System.out.println("2");
                startTime = System.nanoTime();
                tour = Neighbour.neighbour(problem, 0);
                endTime = System.nanoTime() - startTime;
                list.add(new Result(endTime, tour.length(problem)));

                //System.out.println("3");
                startTime = System.nanoTime();
                tour = NeighbourExtended.neighbourExtended(problem);
                endTime = System.nanoTime() - startTime;
                list.add(new Result(endTime, tour.length(problem)));

                //System.out.println("4");
                startTime = System.nanoTime();
                tour = TwoOpt.twoOpt(problem);
                endTime = System.nanoTime() - startTime;
                list.add(new Benchmark.Result(endTime, tour.length(problem)));

                //System.out.println("5");
                // = System.nanoTime();
                //problem.printDistances();
                //tour = TwoOpt.acceleratedTwoOpt(problem);
                //endTime = System.nanoTime() - startTime;
                //list.add(new Benchmark.Result(endTime, tour.length(problem)));
                //System.out.println("6");

            double min = Double.MAX_VALUE;
            for(Benchmark.Result res : list) {
                min = Math.min(min, res.out);
            }

            if(bestKnownSol > 0) {
                min = bestKnownSol;
            }

            for(Benchmark.Result res : list) {
                res.out = (res.out - min) / min;
            }

            for(int i = 0; i < ALG_NUM; i++){
                Benchmark.Result curr = totals.get(i);
                curr.out += list.get(i).out;
                curr.time += list.get(i).time;
            }
        }

        for(int i = 0; i < ALG_NUM; i++){
            Benchmark.Result curr = totals.get(i);
            curr.out/=RPT;
            curr.time/=RPT;
        }

            File file2 = new File("C:\\Users\\Admin\\Desktop\\out\\out-"+file.getName()+"-best-"+(bestKnownSol > 0 ? bestKnownSol : "")+".csv");
            try (PrintWriter pw = new PrintWriter((new FileOutputStream(file2)))) {
                for(int i = 0; i< ALG_NUM;i++)
                    pw.println(i+";"+totals.get(i).time + ";" + totals.get(i).out);
            } catch (Exception e) {
                e.printStackTrace();
            }
    }
}
