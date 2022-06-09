package algorithms.ABC.singleABC.scout;

import algorithms.ABC.singleABC.SingleArtificialBeeColony;
import algorithms.ABC.singleABC.SingleFlower;
import algorithms.ABC.singleABC.SingleMeadow;
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
