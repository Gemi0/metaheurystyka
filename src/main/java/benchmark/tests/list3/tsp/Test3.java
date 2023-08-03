package benchmark.tests.list3.tsp;

import algorithms.arrayTabu.SnapshotData;
import algorithms.arrayTabu.stopConditions.TimeStopCondition;
import algorithms.ABC.singleABC.SingleArtificialBeeColony;
import main.Loader;
import main.TSPData;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;

public class Test3 {

    public static final String NEIGHBORHOOD = "BestGeneticScout";
    public static final String OUTPUT_PATH = "C:\\Users\\Admin\\Desktop\\TSPDATASYMETRIC\\3";
    public static final String SOURCE_PATH = "C:\\Users\\Admin\\Desktop\\TSPDATASYMETRIC\\";
    public static final int REPEATS = 10;
    public static final long TIME_LIMIT = 30000000000L;
    public static final int BEES = 1000;
    public static final int RESET = 500;

    public static void main(String[] args) throws FileNotFoundException {
        System.out.println(NEIGHBORHOOD);
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
            File file2 = new File(OUTPUT_PATH + File.separator + NEIGHBORHOOD + "-" + problemNames.get(i) + ".out");
            if (file2.exists())
                file2.delete();
            File file3 = new File(OUTPUT_PATH + File.separator + NEIGHBORHOOD + "-" + problemNames.get(i) + ".info");
            if (file3.exists())
                file3.delete();

            //Dummy test
            SingleArtificialBeeColony.beeColony(new TimeStopCondition(TIME_LIMIT), problems.get(i), BEES, RESET);

            ArrayList<ArrayList<SnapshotData>> results = new ArrayList<>();
            for (int repeat = 0; repeat < REPEATS; repeat++) {
                System.out.println(repeat);
                SingleArtificialBeeColony.beeColony(new TimeStopCondition(TIME_LIMIT), problems.get(i), BEES, RESET);
                results.add(new ArrayList<>(SingleArtificialBeeColony.snapshotDataArrayList));
            }

            saveResults(results, problemNames.get(i));
        }
    }

    private static void saveResults(ArrayList<ArrayList<SnapshotData>> results, String problemName) throws FileNotFoundException {
        try (PrintWriter pw = new PrintWriter(new FileOutputStream(new File(OUTPUT_PATH + File.separator + NEIGHBORHOOD + "-" + problemName + ".out"), true))) {
            for (int index = 0; index < results.size(); index++) {
                ArrayList<SnapshotData> single = results.get(index);
                for (SnapshotData data : single) {
                    //repeatIndex ; length ; iteration ; time
                    pw.println(index + ";" + data.value() + ";" + data.iteration() + ";" + data.time());
                }
            }
        }

        try (PrintWriter pw = new PrintWriter(new FileOutputStream(new File(OUTPUT_PATH + File.separator + NEIGHBORHOOD + "-" + problemName + ".info"), true))){
            pw.println("REPEATS: " + REPEATS);
            pw.println("TIME_LIMIT: " + TIME_LIMIT);
            pw.println("BEES: " + BEES);
            pw.println("RESET: " + RESET);
            pw.println("PROB: " + 0.3);
        }

    }
}
