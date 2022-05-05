package benchmark.tests.list2.tsp;

import algorithms.arrayTabu.SnapshotData;
import algorithms.arrayTabu.Tabu;
import algorithms.arrayTabu.neighborhoodBrowser.multithreaded.accelerated.AcceleratedInvertMultithreadedBrowser;
import algorithms.arrayTabu.neighborhoodBrowser.multithreaded.accelerated.AcceleratedSwapMultithreadedBrowser;
import algorithms.arrayTabu.neighborhoodBrowser.multithreaded.basic.InsertMultithreadedBrowser;
import algorithms.arrayTabu.neighborhoodBrowser.multithreaded.basic.InvertMultithreadedBrowser;
import algorithms.arrayTabu.neighborhoodBrowser.multithreaded.basic.SwapMultithreadedBrowser;
import algorithms.arrayTabu.neighborhoodBrowser.singlethreaded.accelerated.AcceleratedInvertBrowser;
import algorithms.arrayTabu.neighborhoodBrowser.singlethreaded.accelerated.AcceleratedSwapBrowser;
import algorithms.arrayTabu.neighborhoodBrowser.singlethreaded.basic.InsertBrowser;
import algorithms.arrayTabu.neighborhoodBrowser.singlethreaded.basic.InvertBrowser;
import algorithms.arrayTabu.neighborhoodBrowser.singlethreaded.basic.SwapBrowser;
import algorithms.arrayTabu.stopConditions.TimeStopCondition;
import main.Loader;
import main.TSPData;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;

public class Test4TemplateInvert {

    public static final String PATH = "C:\\Users\\Admin\\Desktop\\TSPDATASYMETRIC\\4\\Insert";
    public static final String SRC = "C:\\Users\\Admin\\Desktop\\TSPDATASYMETRIC\\";

    public static void main(String[] args) throws FileNotFoundException {
        int REPEATS = 10;
        long TIME = 2500000000L;

        File file = new File(SRC);
        ArrayList<TSPData> problems = new ArrayList<>();
        ArrayList<String> problemNames = new ArrayList<>();

        for(File child : file.listFiles()) {
            if(child.getName().endsWith(".xml")) {
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
            //RemoveTestFile!!!!
            File file2 = new File(PATH + File.separator + problemNames.get(i) + ".out");
            if(file2.exists())
                file2.delete();

            //BASE
            ArrayList<ArrayList<SnapshotData>> results = new ArrayList<>();
            for (int repeat = 0; repeat < REPEATS; repeat++) {
                System.out.println(repeat);
                Tabu.tabuSearch(new InsertBrowser(), new TimeStopCondition(TIME), startPermutation, problems.get(i), 7, false, Long.MAX_VALUE);
                results.add(Tabu.snapshots);
            }
            saveResults(results, problemNames.get(i) + ".out", 0);

            //ExtendedNeighbor
            results = new ArrayList<>();
            for (int repeat = 0; repeat < REPEATS; repeat++) {
                System.out.println(repeat);
                Tabu.tabuSearch(new InsertMultithreadedBrowser(), new TimeStopCondition(TIME), startPermutation, problems.get(i), 7, false, Long.MAX_VALUE);
                results.add(Tabu.snapshots);
            }
            saveResults(results, problemNames.get(i) + ".out", 1);

            /*results = new ArrayList<>();
            for (int repeat = 0; repeat < REPEATS; repeat++) {
                System.out.println(repeat);
                Tabu.tabuSearch(new SwapMultithreadedBrowser(), new TimeStopCondition(TIME), startPermutation, problems.get(i), 7, false, Long.MAX_VALUE);
                results.add(Tabu.snapshots);
            }
            saveResults(results, problemNames.get(i) + ".out", 2);

            results = new ArrayList<>();
            for (int repeat = 0; repeat < REPEATS; repeat++) {
                System.out.println(repeat);
                Tabu.tabuSearch(new AcceleratedSwapMultithreadedBrowser(), new TimeStopCondition(TIME), startPermutation, problems.get(i), 7, false, Long.MAX_VALUE);
                results.add(Tabu.snapshots);
            }
            saveResults(results, problemNames.get(i) + ".out", 3);*/

        }
    }

    private static void saveResults(ArrayList<ArrayList<SnapshotData>> results, String filename, int index) throws FileNotFoundException {
        try(PrintWriter pw = new PrintWriter(new FileOutputStream( new File(PATH + File.separator +filename), true))) {
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
