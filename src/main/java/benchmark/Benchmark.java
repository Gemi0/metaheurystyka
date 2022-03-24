package benchmark;

import algorithms.KRandom;
import algorithms.Neighbour;
import algorithms.NeighbourExtended;
import algorithms.TwoOpt;
import main.TSPData;
import main.TSPGenerator;
import main.Tour;

import java.util.ArrayList;

public class Benchmark {

    public static final int MAX_N = 300;
    public static final int RPT = 2;
    public static final double MIN_DST = 1;
    public static final double MAX_DST = 10000;
    public static final int K = 1000;
    public static final int ALG_NUM = 1;

    public static void main(String... args) {
        long startTime = 0;
        long endTime = 0;
        ArrayList<Result> finalTotals[] = new ArrayList[MAX_N + 1];
        finalTotals[0] = null;
        for(int size = 2; size <= MAX_N; size++){
            System.out.println("Currenly processing: " + size);
            ArrayList<Result> totals = new ArrayList<>();
            for(int i = 0; i < ALG_NUM; i++){
                totals.add(new Result(0, 0));
            }
            for(int attempt = 0; attempt < RPT; attempt++) {
                ArrayList<Result> list = new ArrayList<>();
                TSPData problem = TSPGenerator.generateWithoutCoords(MIN_DST, MAX_DST, size, true);

                //System.out.println("1");
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
                list.add(new Result(endTime, tour.length(problem)));

                //System.out.println("5");
                startTime = System.nanoTime();
                //problem.printDistances();
                tour = TwoOpt.acceleratedTwoOpt(problem);
                endTime = System.nanoTime() - startTime;
                list.add(new Result(endTime, tour.length(problem)));
                //System.out.println("6");

                double min = Double.MAX_VALUE;
                for(Result res : list) {
                    min = Math.min(min, res.out);
                }

                for(Result res : list) {
                    res.out = (res.out - min) / min;
                }

                for(int i = 0; i < ALG_NUM; i++){
                    Result curr = totals.get(i);
                    curr.out += list.get(i).out;
                    curr.time += list.get(i).time;
                }
            }

            for(int i = 0; i < ALG_NUM; i++){
                Result curr = totals.get(i);
                curr.out/=RPT;
                curr.time/=RPT;
            }
            finalTotals[size] = totals;
        }
    }

    static class Result{
        public long time;
        public double out;
        public Result(long time, double out){
            this.time = time;
            this.out = out;
        }
    }
}
