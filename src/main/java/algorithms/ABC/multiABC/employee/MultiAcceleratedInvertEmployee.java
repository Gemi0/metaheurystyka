package algorithms.ABC.multiABC.employee;

import algorithms.arrayTabu.neighborhoodBrowser.Util;
import algorithms.ABC.multiABC.MultiFlower;
import algorithms.ABC.multiABC.MultiMeadow;
import main.TSPData;

import java.util.concurrent.ThreadLocalRandom;

public class MultiAcceleratedInvertEmployee extends MultiEmployee {
    public MultiAcceleratedInvertEmployee(MultiMeadow multiMeadow, TSPData data) {
        super(multiMeadow, data);
    }

    @Override
    public void processFlower(MultiFlower flower) {
        int[] currentPermutation = flower.getPermutation();

        int x = ThreadLocalRandom.current().nextInt(currentPermutation.length);
        int y = ThreadLocalRandom.current().nextInt(currentPermutation.length);

        int a = Math.min(x, y);
        int b = Math.max(x, y);

        double newValue = Util.updateInvertValue(currentPermutation, data, flower.getPermutationValue(), a, b);

        if (newValue < flower.getPermutationValue()) {
            int[] newPermutation = new int[currentPermutation.length];
            Util.invert(currentPermutation, newPermutation, a, b);
            flower.setPermutation(newPermutation, newValue);
            if (newValue < multiMeadow.getBestFlower().getPermutationValue()) {
                synchronized (multiMeadow) {
                    if (newValue < multiMeadow.getBestFlower().getPermutationValue()) {
                        multiMeadow.setBestFlower(flower);
                    }
                }
            }
        } else {
            flower.increaseCounter();
        }
    }
}
