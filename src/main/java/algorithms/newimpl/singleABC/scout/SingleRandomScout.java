package algorithms.newimpl.singleABC.scout;

import algorithms.newimpl.singleABC.SingleArtificialBeeColony;
import algorithms.newimpl.singleABC.SingleFlower;
import algorithms.newimpl.singleABC.SingleMeadow;
import main.TSPData;

public class SingleRandomScout extends SingleScout {
    public SingleRandomScout(SingleMeadow singleMeadow, TSPData data, int maxRetries) {
        super(singleMeadow, data, maxRetries);
    }

    @Override
    public void processFlower(SingleFlower flower) {
        if (flower.getRetriesCounter() > maxRetries && flower != singleMeadow.getBestFlower()) {
            SingleArtificialBeeColony.randomizeFlower(flower, data);
            if (flower.getPermutationValue() < singleMeadow.getBestFlower().getPermutationValue()) {
                singleMeadow.setBestFlower(flower);
            }
        }
    }
}
