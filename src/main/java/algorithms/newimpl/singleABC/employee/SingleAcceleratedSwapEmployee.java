package algorithms.newimpl.singleABC.employee;

import algorithms.arrayTabu.neighborhoodBrowser.Util;
import algorithms.newimpl.singleABC.SingleFlower;
import algorithms.newimpl.singleABC.SingleMeadow;
import main.TSPData;

import java.util.concurrent.ThreadLocalRandom;

public class SingleAcceleratedSwapEmployee extends SingleEmployee {
    public SingleAcceleratedSwapEmployee(SingleMeadow singleMeadow, TSPData data) {
        super(singleMeadow, data);
    }

    @Override
    public void processFlower(SingleFlower flower) {
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

            if (newValue < singleMeadow.getBestFlower().getPermutationValue()) {
                singleMeadow.setBestFlower(flower);
            }
        } else {
            flower.increaseCounter();
        }
    }
}
