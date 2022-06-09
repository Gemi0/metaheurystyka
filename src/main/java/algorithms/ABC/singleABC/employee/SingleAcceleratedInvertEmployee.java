package algorithms.ABC.singleABC.employee;

import algorithms.arrayTabu.neighborhoodBrowser.Util;
import algorithms.ABC.singleABC.SingleFlower;
import algorithms.ABC.singleABC.SingleMeadow;
import main.TSPData;

import java.util.concurrent.ThreadLocalRandom;

public class SingleAcceleratedInvertEmployee extends SingleEmployee {
    public SingleAcceleratedInvertEmployee(SingleMeadow singleMeadow, TSPData data) {
        super(singleMeadow, data);
    }

    @Override
    public void processFlower(SingleFlower flower) {
        int[] currentPermutation = flower.getPermutation();

        //TODO: Ważne
        int x = ThreadLocalRandom.current().nextInt(currentPermutation.length);
        int y = ThreadLocalRandom.current().nextInt(currentPermutation.length);

        int a = Math.min(x, y);
        int b = Math.max(x, y);

        double newValue = Util.updateInvertValue(currentPermutation, data, flower.getPermutationValue(), a, b);

        if (newValue < flower.getPermutationValue()) {
            int[] newPermutation = new int[currentPermutation.length];
            Util.invert(currentPermutation, newPermutation, a ,b);
            flower.setPermutation(newPermutation, newValue);

            if (newValue < singleMeadow.getBestFlower().getPermutationValue()) {
                singleMeadow.setBestFlower(flower);
            }
        } else {
            flower.increaseCounter();
        }
    }
}
