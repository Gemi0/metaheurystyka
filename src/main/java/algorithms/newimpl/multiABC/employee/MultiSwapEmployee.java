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

public class MultiSwapEmployee extends MultiEmployee {
    public MultiSwapEmployee(MultiMeadow multiMeadow, TSPData data) {
        super(multiMeadow, data);
    }

    @Override
    public void processFlower(MultiFlower flower) {
        int[] currentPermutation = flower.getPermutation();
        int[] newPermutation = new int[currentPermutation.length];

        Util.swap(currentPermutation, newPermutation, ThreadLocalRandom.current().nextInt(currentPermutation.length), ThreadLocalRandom.current().nextInt(currentPermutation.length));
        double newRouteLenght = Utils.routeLength(newPermutation, data);

        if (newRouteLenght < flower.getPermutationValue()) {
            flower.setPermutation(newPermutation, newRouteLenght);
            if (newRouteLenght < multiMeadow.getBestFlower().getPermutationValue()) {
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

        Util.swap(currentPermutation, newPermutation, ThreadLocalRandom.current().nextInt(currentPermutation.length), ThreadLocalRandom.current().nextInt(currentPermutation.length));
        double newRouteLenght = Utils.routeLength(newPermutation, data);

        if (newRouteLenght < flower.getPermutationValue()) {
            synchronized (flower) {
                if (newRouteLenght < flower.getPermutationValue()) {
                    flower.setPermutation(newPermutation, newRouteLenght);
                    if (newRouteLenght < multiMeadow.getBestFlower().getPermutationValue()) {
                        synchronized (multiMeadow) {
                            if (newRouteLenght < multiMeadow.getBestFlower().getPermutationValue()) {
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
