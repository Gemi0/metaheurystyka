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

public class SingleVariableEmployee extends SingleEmployee {

    public SingleVariableEmployee(SingleMeadow singleMeadow, TSPData data) {
        super(singleMeadow, data);
    }

    @Override
    public void processFlower(SingleFlower flower) {
        int[] currentPermutation = flower.getPermutation();
        int[] newPermutation = new int[currentPermutation.length];

        switch (ThreadLocalRandom.current().nextInt(3)) {
            case(0): Util.invert(currentPermutation, newPermutation, ThreadLocalRandom.current().nextInt(currentPermutation.length), ThreadLocalRandom.current().nextInt(currentPermutation.length));
            case(1): Util.insert(currentPermutation, newPermutation, ThreadLocalRandom.current().nextInt(currentPermutation.length), ThreadLocalRandom.current().nextInt(currentPermutation.length));
            case(2): Util.swap(currentPermutation, newPermutation, ThreadLocalRandom.current().nextInt(currentPermutation.length), ThreadLocalRandom.current().nextInt(currentPermutation.length));

        }


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