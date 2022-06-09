package algorithms.ABC.multiABC.onlooker;

import algorithms.ABC.multiABC.MultiFlower;
import algorithms.ABC.multiABC.MultiMeadow;
import algorithms.ABC.multiABC.employee.MultiEmployee;
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
