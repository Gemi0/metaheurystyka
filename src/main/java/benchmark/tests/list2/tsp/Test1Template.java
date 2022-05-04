package benchmark.tests.list2.tsp;

import algorithms.NeighborExtended;
import algorithms.TwoOpt;
import algorithms.arrayTabu.SnapshotData;
import algorithms.arrayTabu.Tabu;
import algorithms.arrayTabu.neighborhoodBrowser.multithreaded.accelerated.AcceleratedInvertMultithreadedBrowser;
import algorithms.arrayTabu.stopConditions.IterationStopCondition;
import main.Loader;
import main.TSPData;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;

public class Test1Template {

    public static final String PATH = "C:\\Users\\Admin\\Desktop\\TSPDATASYMETRIC\\1";

    public static void main(String[] args) throws FileNotFoundException {
        int REPEATS = 20;
        int ITERATIONS = 10000;

        File file = new File(PATH);
        ArrayList<TSPData> problems = new ArrayList<>();
        ArrayList<String> problemNames = new ArrayList<>();

        for(File child : file.listFiles()) {
            if(child.getName().endsWith("a280.xml")) {
                problems.add(Loader.loadWithoutCoords(child.getPath()));
                problemNames.add(child.getName());
            }
        }

        System.out.println("Loaded: ");
        for(String name : problemNames) {
            System.out.print(name + " ");
        }


        System.out.println();
        for(int i = 0; i < problems.size(); i++) {
            System.out.println(problemNames.get(i));

            int[] startPermutation = new int[problems.get(i).distance.length];
            for (int j = 0; j < problems.get(i).distance.length; j++) {
                startPermutation[j] = j;
            }

            ///TESTS GO HERE

            //BASE
            ArrayList<ArrayList<SnapshotData>> results = new ArrayList<>();
            for (int repeat = 0; repeat < REPEATS; repeat++) {
                System.out.println(repeat);
                Tabu.tabuSearch(new AcceleratedInvertMultithreadedBrowser(), new IterationStopCondition(ITERATIONS), startPermutation, problems.get(i), 7, false, Long.MAX_VALUE);
                results.add(Tabu.snapshots);
            }
            saveResults(results, problemNames.get(i) + ".out", 0);

            //ExtendedNeighbor
            results = new ArrayList<>();
            for (int repeat = 0; repeat < REPEATS; repeat++) {
                System.out.println(repeat);
                Tabu.tabuSearch(new AcceleratedInvertMultithreadedBrowser(), new IterationStopCondition(ITERATIONS), NeighborExtended.neighborExtended(problems.get(i)), problems.get(i), 7, false, Long.MAX_VALUE);
                results.add(Tabu.snapshots);
            }
            saveResults(results, problemNames.get(i) + ".out", 1);

            results = new ArrayList<>();
            for (int repeat = 0; repeat < REPEATS; repeat++) {
                System.out.println(repeat);
                Tabu.tabuSearch(new AcceleratedInvertMultithreadedBrowser(), new IterationStopCondition(ITERATIONS), TwoOpt.twoOpt(problems.get(i), startPermutation), problems.get(i), 7, false, Long.MAX_VALUE);
                results.add(Tabu.snapshots);
            }
            saveResults(results, problemNames.get(i) + ".out", 2);

        }
    }

    private static void saveResults(ArrayList<ArrayList<SnapshotData>> results, String filename, int index) throws FileNotFoundException {
        try(PrintWriter pw = new PrintWriter(new FileOutputStream(new File(PATH + File.separator +filename), true))) {
            for (ArrayList<SnapshotData> single : results) {
                double minValue = Double.MAX_VALUE;
                for (SnapshotData data : single) {
                    minValue = Math.min(data.value(), minValue);
                    pw.println(index + ";" + minValue+";"+data.iteration()+";"+data.time());
                }
            }
        }
    }
}
