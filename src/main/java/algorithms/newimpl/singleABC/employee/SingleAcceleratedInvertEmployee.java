package algorithms.newimpl.singleABC.employee;

import algorithms.Utils;
import algorithms.arrayTabu.neighborhoodBrowser.Util;
import algorithms.newimpl.singleABC.SingleFlower;
import algorithms.newimpl.singleABC.SingleMeadow;
import main.TSPData;

import java.util.concurrent.ThreadLocalRandom;

public class SingleAcceleratedInvertEmployee extends SingleEmployee {
    public SingleAcceleratedInvertEmployee(SingleMeadow singleMeadow, TSPData data) {
        super(singleMeadow, data);
    }

    @Override
    public void processFlower(SingleFlower flower) {
        int[] currentPermutation = flower.getPermutation();
        int[] newPermutation = new int[currentPermutation.length];

        int a = ThreadLocalRandom.current().nextInt(currentPermutation.length);
        int b = ThreadLocalRandom.current().nextInt(currentPermutation.length);

        double newValue = Util.updateInvertValue(currentPermutation, data, flower.getPermutationValue(), a, b);

        if (newValue < flower.getPermutationValue()) {
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
