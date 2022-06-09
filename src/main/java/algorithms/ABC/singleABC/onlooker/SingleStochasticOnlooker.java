package algorithms.ABC.singleABC.onlooker;

import algorithms.ABC.singleABC.SingleFlower;
import algorithms.ABC.singleABC.SingleMeadow;
import algorithms.ABC.singleABC.employee.SingleEmployee;
import main.TSPData;

import java.util.concurrent.ThreadLocalRandom;

public class SingleStochasticOnlooker extends SingleOnlooker {

    public SingleStochasticOnlooker(SingleMeadow singleMeadow, TSPData data, SingleEmployee singleEmployee) {
        super(singleMeadow, data, singleEmployee);
    }

    @Override
    public SingleFlower roulette() {
        SingleFlower flower;
        while (true) {
            flower = singleMeadow.getFlowers().get(ThreadLocalRandom.current().nextInt(singleMeadow.getFlowers().size()));
            if ((1/flower.getPermutationValue())/(1/ singleMeadow.getBestFlower().getPermutationValue()) >= ThreadLocalRandom.current().nextDouble()) {
                break;
            }
        }
        return flower;
    }
}
