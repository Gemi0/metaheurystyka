package algorithms.newimpl.multiABC;

import algorithms.Utils;
import algorithms.arrayTabu.SnapshotData;
import algorithms.arrayTabu.stopConditions.StopCondition;
import algorithms.newimpl.multiABC.employee.MultiAcceleratedInvertEmployee;
import algorithms.newimpl.multiABC.employee.MultiEmployee;
import algorithms.newimpl.multiABC.employee.MultiInvertEmployee;
import algorithms.newimpl.multiABC.onlooker.MultiOnlooker;
import algorithms.newimpl.multiABC.onlooker.MultiStochasticOnlooker;
import algorithms.newimpl.multiABC.scout.MultiRandomScout;
import algorithms.newimpl.multiABC.scout.MultiScout;
import main.TSPData;

import java.util.ArrayList;
import java.util.Arrays;

public class MultiArtificialBeeColony {

    public static ArrayList<SnapshotData> snapshotDataArrayList = new ArrayList<>();

    public static int[] beeColony(StopCondition condition, TSPData data, int flowersNumber, int maxRetriesCounter) {
        snapshotDataArrayList.clear();
        MultiMeadow multiMeadow = new MultiMeadow();
        MultiEmployee employee = new MultiInvertEmployee(multiMeadow, data);
        MultiOnlooker onlooker = new MultiStochasticOnlooker(multiMeadow, data, employee);
        MultiScout scout = new MultiRandomScout(multiMeadow, data, maxRetriesCounter);

        long algorithmIteration = 0;
        long startTime = System.nanoTime();
        long runtime = 0;
        randomizeMeadow(multiMeadow, flowersNumber, data);
        do {
            employee.sendBees();
            onlooker.sendBees();
            scout.sendBees();

            algorithmIteration++;
            runtime = System.nanoTime() - startTime;
            snapshotDataArrayList.add(new SnapshotData(algorithmIteration, runtime, multiMeadow.getBestFlower().getPermutationValue()));
        } while(!condition.shouldStop(algorithmIteration, runtime));

        return multiMeadow.getBestFlower().getPermutation();
    }

    //TODO: Parallelize?
    public static void randomizeMeadow(MultiMeadow multiMeadow, int flowersNumber, TSPData data) {
        int[] temporary = new int[data.distance.length];
        for(int i = 0; i < temporary.length; i++){
            temporary[i] = i;
        }
        for(int i = 0; i < flowersNumber; i++){
            MultiFlower multiFlower = new MultiFlower(Arrays.copyOf(temporary, temporary.length), 0);
            randomizeFlower(multiFlower, data);
            multiMeadow.getFlowers().add(multiFlower);
            if(multiMeadow.getBestFlower() == null || multiMeadow.getBestFlower().getPermutationValue() > multiFlower.getPermutationValue()) {
                multiMeadow.setBestFlower(multiFlower);
            }
        }
    }

    public static void randomizeFlower(MultiFlower flower, TSPData data) {
        int[] currentPermutation = flower.getPermutation();
        Utils.shuffle(currentPermutation);
        flower.setPermutation(currentPermutation, Utils.routeLength(currentPermutation, data));
    }
}
