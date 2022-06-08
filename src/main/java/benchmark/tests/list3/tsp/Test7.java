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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;

public class Test7 {
    private static final Set<Integer> resetValues = new TreeSet<>(Arrays.asList(100, 1000, 10000, 100000, 1000000));
    public static final String OUTPUT_PATH = "C:\\Users\\Admin\\Desktop\\TSPDATASYMETRIC\\7";
    public static final String SOURCE_PATH = "C:\\Users\\Admin\\Desktop\\TSPDATASYMETRIC\\";
    public static final long TIME_LIMIT = 300000000000L;
    public static final int BEES = 1000;

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
            File file3 = new File(OUTPUT_PATH + File.separator + problemNames.get(i) + ".info");
            if (file3.exists())
                file3.delete();

            //Dummy test
            MultiArtificialBeeColony.beeColony(new TimeStopCondition(30000000000L), problems.get(i), BEES, 1024);

            for (int reset : resetValues) {
                System.out.println(reset);
                MultiArtificialBeeColony.beeColony(new TimeStopCondition(TIME_LIMIT), problems.get(i), BEES, reset);
                saveResults(MultiArtificialBeeColony.snapshotDataArrayList, problemNames.get(i), reset);
            }

        }
    }

    private static void saveResults(ArrayList<SnapshotData> results, String problemName, int reset) throws FileNotFoundException {
        try (PrintWriter pw = new PrintWriter(new FileOutputStream(new File(OUTPUT_PATH + File.separator + problemName + ".out"), true))) {
                for (SnapshotData data : results) {
                    pw.println(reset + ";" + data.value() + ";" + data.iteration() + ";" + data.time());
                }
        }

        try (PrintWriter pw = new PrintWriter(new FileOutputStream(new File(OUTPUT_PATH + File.separator + problemName + ".info"), true))){
            pw.println("TIME_LIMIT: " + TIME_LIMIT);
            pw.println("BEES: " + BEES);
        }

    }
}
