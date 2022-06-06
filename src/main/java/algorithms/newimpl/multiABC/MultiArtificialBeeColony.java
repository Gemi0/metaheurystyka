package algorithms.newimpl.multiABC;

import algorithms.Utils;
import algorithms.arrayTabu.stopConditions.StopCondition;
import algorithms.newimpl.multiABC.employee.MultiEmployee;
import algorithms.newimpl.multiABC.employee.MultiInvertEmployee;
import algorithms.newimpl.multiABC.onlooker.MultiOnlooker;
import algorithms.newimpl.multiABC.onlooker.MultiStochasticOnlooker;
import algorithms.newimpl.multiABC.scout.MultiRandomScout;
import algorithms.newimpl.multiABC.scout.MultiScout;
import main.TSPData;

import java.util.Arrays;

public class MultiArtificialBeeColony {

    public static int[] beeColony(StopCondition condition, TSPData data, int flowersNumber, int maxRetriesCounter) {
        MultiMeadow multiMeadow = new MultiMeadow();
        MultiEmployee employee = new MultiInvertEmployee(multiMeadow, data);
        MultiOnlooker onlooker = new MultiStochasticOnlooker(multiMeadow, data, employee);
        MultiScout scout = new MultiRandomScout(multiMeadow, data, maxRetriesCounter);

        randomizeMeadow(multiMeadow, flowersNumber, data);

        long algorithmIteration = 0;
        long startTime = System.nanoTime();
        do {
            employee.sendBees();
            onlooker.sendBees();
            scout.sendBees();

            algorithmIteration++;
        } while(!condition.shouldStop(algorithmIteration, System.nanoTime() - startTime));

        System.out.println(algorithmIteration);
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
