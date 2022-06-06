package algorithms.newimpl.singleABC.employee;

import algorithms.Utils;
import algorithms.arrayTabu.neighborhoodBrowser.Util;
import algorithms.newimpl.singleABC.SingleFlower;
import algorithms.newimpl.singleABC.SingleMeadow;
import main.TSPData;

import java.util.concurrent.ThreadLocalRandom;

public class SingleInvertEmployee extends SingleEmployee {
    public SingleInvertEmployee(SingleMeadow singleMeadow, TSPData data) {
        super(singleMeadow, data);
    }

    @Override
    public void processFlower(SingleFlower flower) {
        int[] currentPermutation = flower.getPermutation();
        int[] newPermutation = new int[currentPermutation.length];

        Util.invert(currentPermutation, newPermutation, ThreadLocalRandom.current().nextInt(currentPermutation.length), ThreadLocalRandom.current().nextInt(currentPermutation.length));
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
