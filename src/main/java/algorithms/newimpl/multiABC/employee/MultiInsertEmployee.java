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

public class MultiInsertEmployee extends MultiEmployee {
    public MultiInsertEmployee(MultiMeadow multiMeadow, TSPData data) {
        super(multiMeadow, data);
    }

    @Override
    public void processFlower(MultiFlower flower) {
        int[] currentPermutation = flower.getPermutation();
        int[] newPermutation = new int[currentPermutation.length];

        int x = ThreadLocalRandom.current().nextInt(currentPermutation.length);
        int y = ThreadLocalRandom.current().nextInt(currentPermutation.length);

        int a = Math.min(x, y);
        int b = Math.max(x, y);

        Util.insert(currentPermutation, newPermutation, a, b);
        double newRouteLenght = Utils.routeLength(newPermutation, data);

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
        else {
            flower.increaseCounter();
        }
    }
}
