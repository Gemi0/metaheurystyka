package algorithms.newimpl.multiABC.scout;

import algorithms.newimpl.multiABC.MultiArtificialBeeColony;
import algorithms.newimpl.multiABC.MultiFlower;
import algorithms.newimpl.multiABC.MultiMeadow;
import main.TSPData;

public class MultiRandomScout extends MultiScout{
    public MultiRandomScout(MultiMeadow multiMeadow, TSPData data, int maxRetries) {
        super(multiMeadow, data, maxRetries);
    }

    @Override
    public void processFlower(MultiFlower flower) {
        if (flower.getRetriesCounter() > maxRetries && flower != multiMeadow.getBestFlower()) {
            MultiArtificialBeeColony.randomizeFlower(flower, data);
            if (flower.getPermutationValue() < multiMeadow.getBestFlower().getPermutationValue()) {
                synchronized (multiMeadow) {
                    if (flower.getPermutationValue() < multiMeadow.getBestFlower().getPermutationValue()) {
                        multiMeadow.setBestFlower(flower);
                    }
                }
            }
        }
    }
}
