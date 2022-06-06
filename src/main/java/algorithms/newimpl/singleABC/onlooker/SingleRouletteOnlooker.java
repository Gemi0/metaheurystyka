package algorithms.newimpl.singleABC.onlooker;

import algorithms.newimpl.multiABC.MultiFlower;
import algorithms.newimpl.singleABC.SingleFlower;
import algorithms.newimpl.singleABC.SingleMeadow;
import algorithms.newimpl.singleABC.employee.SingleEmployee;
import main.TSPData;

import java.util.TreeMap;
import java.util.concurrent.ThreadLocalRandom;

public class SingleRouletteOnlooker extends SingleOnlooker{

    TreeMap<Double, SingleFlower> probabilitiesMap;

    public SingleRouletteOnlooker(SingleMeadow singleMeadow, TSPData data, SingleEmployee singleEmployee) {
        super(singleMeadow, data, singleEmployee);
    }

    @Override
    public SingleFlower roulette() {
        calculateProbabilities();
        double r = ThreadLocalRandom.current().nextDouble();
        double key;
        try {
            key = probabilitiesMap.floorKey(r);
        }
        catch (NullPointerException e ) {
            key = probabilitiesMap.ceilingKey(0.0);
        }
        return probabilitiesMap.get(key);
    }



    private void calculateProbabilities() {
        probabilitiesMap = new TreeMap<>();
        double sum =0;
        for (SingleFlower f : singleMeadow.getFlowers()) {
            sum += 1 / f.getPermutationValue();
        }
        double lastVal = (1/singleMeadow.getFlowers().get(0).getPermutationValue())/sum;
        probabilitiesMap.put(lastVal, singleMeadow.getFlowers().get(0));
        for (int i = 1; i < singleMeadow.getFlowers().size(); i++) {
            probabilitiesMap.put((1/singleMeadow.getFlowers().get(i).getPermutationValue())/sum + lastVal, singleMeadow.getFlowers().get(i));
            lastVal = (1/singleMeadow.getFlowers().get(i).getPermutationValue())/sum + lastVal;
        }
    }
}
