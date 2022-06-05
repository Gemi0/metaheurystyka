package algorithms.newimpl.multiABC.onlooker;

import algorithms.newimpl.multiABC.MultiFlower;
import algorithms.newimpl.multiABC.MultiMeadow;
import algorithms.newimpl.multiABC.employee.MultiEmployee;
import main.TSPData;

import java.util.concurrent.ThreadLocalRandom;

public class MultiStochasticOnlooker extends MultiOnlooker{

    public MultiStochasticOnlooker(MultiMeadow multiMeadow, TSPData data, MultiEmployee multiEmployee) {
        super(multiMeadow, data, multiEmployee);
    }

    @Override
    public MultiFlower roulette() {
        MultiFlower flower;
        while (true) {
            flower = multiMeadow.getFlowers().get(ThreadLocalRandom.current().nextInt(multiMeadow.getFlowers().size()));
            if ((1/flower.getPermutationValue())/(1/ multiMeadow.getBestFlower().getPermutationValue()) >= ThreadLocalRandom.current().nextDouble()) {
                break;
            }
        }
        return flower;
    }
}
