package algorithms.newimpl.multiABC.employee;

import algorithms.Utils;
import algorithms.arrayTabu.neighborhoodBrowser.Util;
import algorithms.newimpl.multiABC.MultiFlower;
import algorithms.newimpl.multiABC.MultiMeadow;
import algorithms.newimpl.singleABC.SingleFlower;
import algorithms.newimpl.singleABC.SingleMeadow;
import algorithms.newimpl.singleABC.employee.SingleEmployee;
import main.TSPData;

import java.util.concurrent.ThreadLocalRandom;

public class MultiAcceleratedSwapEmployee extends MultiEmployee {
    public MultiAcceleratedSwapEmployee(MultiMeadow multiMeadow, TSPData data) {
        super(multiMeadow, data);
    }

    @Override
    public void processFlower(MultiFlower flower) {
        int[] currentPermutation = flower.getPermutation();

        int x = ThreadLocalRandom.current().nextInt(currentPermutation.length);
        int y = ThreadLocalRandom.current().nextInt(currentPermutation.length);

        int a = Math.min(x, y);
        int b = Math.max(x, y);

        double newValue = Util.updateSwapValue(currentPermutation, data, flower.getPermutationValue(), a, b);

        if (newValue < flower.getPermutationValue()) {
            int[] newPermutation = new int[currentPermutation.length];
            Util.swap(currentPermutation, newPermutation, a ,b);
            flower.setPermutation(newPermutation, newValue);
            if (newValue < multiMeadow.getBestFlower().getPermutationValue()) {
                synchronized (multiMeadow) {
                    if (newValue < multiMeadow.getBestFlower().getPermutationValue()) {
                        multiMeadow.setBestFlower(flower);
                    }
                }
            }
        }
        else {
            flower.increaseCounter();
        }
    }

    @Override
    public void processFlowerSynchronized(MultiFlower flower) {
        int[] currentPermutation = flower.getPermutation();


        int x = ThreadLocalRandom.current().nextInt(currentPermutation.length);
        int y = ThreadLocalRandom.current().nextInt(currentPermutation.length);

        int a = Math.min(x, y);
        int b = Math.max(x, y);

        double newValue = Util.updateSwapValue(currentPermutation, data, flower.getPermutationValue(), a, b);

        if (newValue < flower.getPermutationValue()) {
            synchronized (flower) {
                if (newValue < flower.getPermutationValue()) {
                    int[] newPermutation = new int[currentPermutation.length];
                    Util.swap(currentPermutation, newPermutation, a ,b);
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
