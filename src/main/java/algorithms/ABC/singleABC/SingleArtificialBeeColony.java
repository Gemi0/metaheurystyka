package algorithms.newimpl.singleABC;

import algorithms.Utils;
import algorithms.arrayTabu.SnapshotData;
import algorithms.arrayTabu.stopConditions.StopCondition;
import algorithms.newimpl.singleABC.employee.*;
import algorithms.newimpl.singleABC.onlooker.SingleOnlooker;
import algorithms.newimpl.singleABC.onlooker.SingleRouletteOnlooker;
import algorithms.newimpl.singleABC.onlooker.SingleStochasticOnlooker;
import algorithms.newimpl.singleABC.scout.SingleBestGeneticScout;
import algorithms.newimpl.singleABC.scout.SingleGeneticScout;
import algorithms.newimpl.singleABC.scout.SingleRandomScout;
import algorithms.newimpl.singleABC.scout.SingleScout;
import main.TSPData;

import java.util.ArrayList;
import java.util.Arrays;

public class SingleArtificialBeeColony {

    public static ArrayList<SnapshotData> snapshotDataArrayList = new ArrayList<>();

    public static int[] beeColony(StopCondition condition, TSPData data, int flowersNumber, int maxRetriesCounter) {
        snapshotDataArrayList.clear();
        SingleMeadow singleMeadow = new SingleMeadow();
        SingleEmployee employee = new SingleAcceleratedInvertEmployee(singleMeadow, data);
        SingleOnlooker onlooker = new SingleStochasticOnlooker(singleMeadow, data, employee);
        SingleScout scout = new SingleRandomScout(singleMeadow, data, maxRetriesCounter);

        long algorithmIteration = 0;
        long startTime = System.nanoTime();
        long runtime = 0;
        randomizeMeadow(singleMeadow, flowersNumber, data);
        do {
            employee.sendBees();
            onlooker.sendBees();
            scout.sendBees();
            algorithmIteration++;
            runtime = System.nanoTime() - startTime;
            snapshotDataArrayList.add(new SnapshotData(algorithmIteration, runtime, singleMeadow.getBestFlower().getPermutationValue()));
        } while(!condition.shouldStop(algorithmIteration, runtime));

        return singleMeadow.getBestFlower().getPermutation();
    }

    public static void randomizeMeadow(SingleMeadow singleMeadow, int flowersNumber, TSPData data) {
        int[] temporary = new int[data.distance.length];
        for(int i = 0; i < temporary.length; i++){
            temporary[i] = i;
        }
        for(int i = 0; i < flowersNumber; i++){
            SingleFlower singleFlower = new SingleFlower(Arrays.copyOf(temporary, temporary.length), 0);
            randomizeFlower(singleFlower, data);
            singleMeadow.getFlowers().add(singleFlower);
            if(singleMeadow.getBestFlower() == null || singleMeadow.getBestFlower().getPermutationValue() > singleFlower.getPermutationValue()) {
                singleMeadow.setBestFlower(singleFlower);
            }
        }
    }

    public static void randomizeFlower(SingleFlower flower, TSPData data) {
        int[] currentPermutation = flower.getPermutation();
        Utils.shuffle(currentPermutation);
        flower.setPermutation(currentPermutation, Utils.routeLength(currentPermutation, data));
    }
}
