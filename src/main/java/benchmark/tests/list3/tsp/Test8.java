package benchmark.tests.list3.tsp;

import algorithms.arrayTabu.SnapshotData;
import algorithms.arrayTabu.Tabu;
import algorithms.arrayTabu.neighborhoodBrowser.multithreaded.basic.InvertMultithreadedBrowser;
import algorithms.arrayTabu.neighborhoodBrowser.singlethreaded.basic.InvertBrowser;
import algorithms.arrayTabu.stopConditions.TimeStopCondition;
import algorithms.newimpl.multiABC.MultiArtificialBeeColony;
import main.Loader;
import main.TSPData;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;

public class Test8 {
    public static final String OUTPUT_PATH = "C:\\Users\\Admin\\Desktop\\TSPDATASYMETRIC\\8";
    public static final String SOURCE_PATH = "C:\\Users\\Admin\\Desktop\\TSPDATASYMETRIC\\";
    public static final long TIME_LIMIT = 300000000000L;
    public static final int BEES = 1000;
    public static int RESET = -1;

    public static void main(String[] args) throws FileNotFoundException {
        File file = new File(SOURCE_PATH);
        ArrayList<TSPData> problems = new ArrayList<>();
        ArrayList<String> problemNames = new ArrayList<>();

        for (File child : file.listFiles()) {
            if (child.getName().endsWith(".xml")) {
                problems.add(Loader.loadWithoutCoords(child.getPath()));
                problemNames.add(child.getName());
            }
        }

        System.out.println("Loaded: ");
        for (String name : problemNames) {
            System.out.print(name + " ");
        }

        System.out.println();
        for (int i = 0; i < problems.size(); i++) {
            System.out.println(problemNames.get(i));
            RESET = (problems.get(i).distance.length >= 200 ? 100000 : 10000);
            //RemoveTestFile!!!!
            File file2 = new File(OUTPUT_PATH + File.separator + problemNames.get(i) + ".out");
            if (file2.exists())
                file2.delete();
            File file3 = new File(OUTPUT_PATH + File.separator + problemNames.get(i) + ".info");
            if (file3.exists())
                file3.delete();

            int[] startPermutation = new int[problems.get(i).distance.length];
            for (int j = 0; j < problems.get(i).distance.length; j++) {
                startPermutation[j] = j;
            }

            //Dummy test
            MultiArtificialBeeColony.beeColony(new TimeStopCondition(5000000000L), problems.get(i), BEES, RESET);

            System.out.println("bees");
            MultiArtificialBeeColony.beeColony(new TimeStopCondition(TIME_LIMIT), problems.get(i), BEES, RESET);
            saveResults(MultiArtificialBeeColony.snapshotDataArrayList, problemNames.get(i), "bees");

            //Dummy test
            Tabu.tabuSearch(new InvertMultithreadedBrowser(), new TimeStopCondition(5000000000L), startPermutation, problems.get(i), 2 * problems.get(i).distance.length, false, 100);

            System.out.println("best-tabu");
            Tabu.tabuSearch(new InvertMultithreadedBrowser(), new TimeStopCondition(TIME_LIMIT), startPermutation, problems.get(i), 2 * problems.get(i).distance.length, false, 100);
            saveResults(Tabu.snapshots, problemNames.get(i), "best-tabu");

            //Dummy test
            Tabu.tabuSearch(new InvertBrowser(), new TimeStopCondition(5000000000L), startPermutation, problems.get(i), 7, false, Integer.MAX_VALUE);

            System.out.println("basic-tabu");
            Tabu.tabuSearch(new InvertBrowser(), new TimeStopCondition(TIME_LIMIT), startPermutation, problems.get(i), 7, false, Integer.MAX_VALUE);
            saveResults(Tabu.snapshots, problemNames.get(i), "basic-tabu");
        }
    }

    private static void saveResults(ArrayList<SnapshotData> results, String problemName, String name) throws FileNotFoundException {
        try (PrintWriter pw = new PrintWriter(new FileOutputStream(new File(OUTPUT_PATH + File.separator + problemName + ".out"), true))) {
                for (SnapshotData data : results) {
                    pw.println(name + ";" + data.value() + ";" + data.iteration() + ";" + data.time());
                }
        }

        try (PrintWriter pw = new PrintWriter(new FileOutputStream(new File(OUTPUT_PATH + File.separator + problemName + ".info"), true))){
            pw.println("problemName: " + TIME_LIMIT);
            pw.println("TIME_LIMIT: " + TIME_LIMIT);
            pw.println("BEES: " + BEES);
            pw.println("RESET: " + BEES);
            pw.println();
        }

    }
}
