package benchmark.tests.list3.tsp;

import algorithms.arrayTabu.SnapshotData;
import algorithms.arrayTabu.stopConditions.TimeStopCondition;
import algorithms.newimpl.multiABC.MultiArtificialBeeColony;
import main.Loader;
import main.TSPData;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.*;

public class Test6 {
    private static final Set<Integer> beesSet = new TreeSet<>(Arrays.asList(100, 1000, 10000, 25000, 50000));
    public static final String OUTPUT_PATH = "C:\\Users\\Admin\\Desktop\\TSPDATASYMETRIC\\6";
    public static final String SOURCE_PATH = "C:\\Users\\Admin\\Desktop\\TSPDATASYMETRIC\\";
    public static final long TIME_LIMIT = 300000000000L;
    public static final int RESET = 10000;

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
            //RemoveTestFile!!!!
            File file2 = new File(OUTPUT_PATH + File.separator + problemNames.get(i) + ".out");
            if (file2.exists())
                file2.delete();
            File file3 = new File(OUTPUT_PATH + File.separator + "-" + problemNames.get(i) + ".info");
            if (file3.exists())
                file3.delete();

            //Dummy test
            MultiArtificialBeeColony.beeColony(new TimeStopCondition(30000000000L), problems.get(i), 1024, RESET);

            for (int bees : beesSet) {
                System.out.println(bees);
                MultiArtificialBeeColony.beeColony(new TimeStopCondition(TIME_LIMIT), problems.get(i), bees, RESET);
                saveResults(MultiArtificialBeeColony.snapshotDataArrayList, problemNames.get(i), bees);
            }

        }
    }

    private static void saveResults(ArrayList<SnapshotData> results, String problemName, int bees) throws FileNotFoundException {
        try (PrintWriter pw = new PrintWriter(new FileOutputStream(new File(OUTPUT_PATH + File.separator + problemName + ".out"), true))) {
                for (SnapshotData data : results) {
                    pw.println(bees + ";" + data.value() + ";" + data.iteration() + ";" + data.time());
                }
        }

        try (PrintWriter pw = new PrintWriter(new FileOutputStream(new File(OUTPUT_PATH + File.separator + problemName + ".info"), true))){
            pw.println("TIME_LIMIT: " + TIME_LIMIT);
            pw.println("RESET: " + RESET);
        }

    }
}
