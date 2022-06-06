package algorithms.newimpl.singleABC.employee;

import algorithms.Utils;
import algorithms.arrayTabu.neighborhoodBrowser.Util;
import algorithms.newimpl.multiABC.MultiFlower;
import algorithms.newimpl.multiABC.MultiMeadow;
import algorithms.newimpl.multiABC.employee.MultiEmployee;
import algorithms.newimpl.singleABC.SingleFlower;
import algorithms.newimpl.singleABC.SingleMeadow;
import main.TSPData;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class SingleVariableEmployee extends MultiEmployee {

    private final Random rnd;

    public SingleVariableEmployee(MultiMeadow multiMeadow, TSPData data) {
        super(multiMeadow, data);
        rnd = new Random();
    }

    @Override
    public void processFlower(MultiFlower flower) {
        int[] currentPermutation = flower.getPermutation();
        int[] newPermutation = new int[currentPermutation.length];

        switch (rnd.nextInt(3)) {
            case(0): Util.invert(currentPermutation, newPermutation, ThreadLocalRandom.current().nextInt(currentPermutation.length), ThreadLocalRandom.current().nextInt(currentPermutation.length));
            case(1): Util.insert(currentPermutation, newPermutation, ThreadLocalRandom.current().nextInt(currentPermutation.length), ThreadLocalRandom.current().nextInt(currentPermutation.length));
            case(2): Util.swap(currentPermutation, newPermutation, ThreadLocalRandom.current().nextInt(currentPermutation.length), ThreadLocalRandom.current().nextInt(currentPermutation.length));

        }


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

        switch (rnd.nextInt(3)) {
            case(0): Util.invert(currentPermutation, newPermutation, ThreadLocalRandom.current().nextInt(currentPermutation.length), ThreadLocalRandom.current().nextInt(currentPermutation.length));
            case(1): Util.insert(currentPermutation, newPermutation, ThreadLocalRandom.current().nextInt(currentPermutation.length), ThreadLocalRandom.current().nextInt(currentPermutation.length));
            case(2): Util.swap(currentPermutation, newPermutation, ThreadLocalRandom.current().nextInt(currentPermutation.length), ThreadLocalRandom.current().nextInt(currentPermutation.length));

        }
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