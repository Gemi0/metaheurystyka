package algorithms.newimpl.multiABC.employee;

import algorithms.arrayTabu.neighborhoodBrowser.Util;
import algorithms.newimpl.multiABC.MultiFlower;
import algorithms.newimpl.multiABC.MultiMeadow;
import algorithms.newimpl.singleABC.SingleFlower;
import algorithms.newimpl.singleABC.SingleMeadow;
import algorithms.newimpl.singleABC.employee.SingleEmployee;
import main.TSPData;

import java.util.concurrent.ThreadLocalRandom;

public class MultiAcceleratedInvertEmployee extends MultiEmployee {
    public MultiAcceleratedInvertEmployee(MultiMeadow multiMeadow, TSPData data) {
        super(multiMeadow, data);
    }

    @Override
    public void processFlower(MultiFlower flower) {
        int[] currentPermutation = flower.getPermutation();
        int[] newPermutation = new int[currentPermutation.length];

        int a = ThreadLocalRandom.current().nextInt(currentPermutation.length);
        int b = ThreadLocalRandom.current().nextInt(currentPermutation.length);

        double newValue = Util.updateInvertValue(currentPermutation, data, flower.getPermutationValue(), a, b);

        if (newValue < flower.getPermutationValue()) {
            Util.invert(currentPermutation, newPermutation, a ,b);
            flower.setPermutation(newPermutation, newValue);

            if (newValue < multiMeadow.getBestFlower().getPermutationValue()) {
                multiMeadow.setBestFlower(flower);
            }
        } else {
            flower.increaseCounter();
        }
    }

    @Override
    public void processFlowerSynchronized(MultiFlower flower) {
        int[] currentPermutation = flower.getPermutation();
        int[] newPermutation = new int[currentPermutation.length];

        int a = ThreadLocalRandom.current().nextInt(currentPermutation.length);
        int b = ThreadLocalRandom.current().nextInt(currentPermutation.length);

        double newValue = Util.updateInvertValue(currentPermutation, data, flower.getPermutationValue(), a, b);

        if (newValue < flower.getPermutationValue()) {
            synchronized (flower) {
                if (newValue < flower.getPermutationValue()) {
                    Util.invert(currentPermutation, newPermutation, a ,b);
                    flower.setPermutation(newPermutation, newValue);
                    if (newValue < multiMeadow.getBestFlower().getPermutationValue()) {
                        synchronized (multiMeadow) {
                            if (newValue < multiMeadow.getBestFlower().getPermutationValue()) {
                                multiMeadow.setBestFlower(flower);
                            }
                        }
                    }
                }
            }
        }
        else {
            flower.increaseCounter();
        }
    }
}
