package algorithms.ABC.singleABC.employee;

import algorithms.Utils;
import algorithms.arrayTabu.neighborhoodBrowser.Util;
import algorithms.ABC.singleABC.SingleFlower;
import algorithms.ABC.singleABC.SingleMeadow;
import main.TSPData;

import java.util.concurrent.ThreadLocalRandom;

public class SingleSwapEmployee extends SingleEmployee {
    public SingleSwapEmployee(SingleMeadow singleMeadow, TSPData data) {
        super(singleMeadow, data);
    }

    @Override
    public void processFlower(SingleFlower flower) {
        int[] currentPermutation = flower.getPermutation();
        int[] newPermutation = new int[currentPermutation.length];

        int x = ThreadLocalRandom.current().nextInt(currentPermutation.length);
        int y = ThreadLocalRandom.current().nextInt(currentPermutation.length);

        int a = Math.min(x, y);
        int b = Math.max(x, y);

        Util.swap(currentPermutation, newPermutation, a, b);
        double newRouteLenght = Utils.routeLength(newPermutation, data);

        if (newRouteLenght < flower.getPermutationValue()) {
            flower.setPermutation(newPermutation, newRouteLenght);
            if (newRouteLenght < singleMeadow.getBestFlower().getPermutationValue()) {
                singleMeadow.setBestFlower(flower);
            }
        } else {
            flower.increaseCounter();
        }
    }
}
