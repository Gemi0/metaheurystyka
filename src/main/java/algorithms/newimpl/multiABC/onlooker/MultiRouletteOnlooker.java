package algorithms.newimpl.multiABC.onlooker;

import algorithms.ABC.Flower;
import algorithms.newimpl.multiABC.MultiFlower;
import algorithms.newimpl.multiABC.MultiMeadow;
import algorithms.newimpl.multiABC.employee.MultiEmployee;
import main.TSPData;

import java.util.ArrayList;
import java.util.TreeMap;
import java.util.concurrent.ThreadLocalRandom;

public class MultiRouletteOnlooker extends MultiOnlooker{

    TreeMap<Double, MultiFlower> probabilitiesMap;

    public MultiRouletteOnlooker(MultiMeadow multiMeadow, TSPData data, MultiEmployee multiEmployee) {
        super(multiMeadow, data, multiEmployee);
    }

    @Override
    public MultiFlower roulette() {
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
        for (MultiFlower f : multiMeadow.getFlowers()) {
            sum += 1 / f.getPermutationValue();
        }
        double lastVal = (1/multiMeadow.getFlowers().get(0).getPermutationValue())/sum;
        probabilitiesMap.put(lastVal, multiMeadow.getFlowers().get(0));
        for (int i = 1; i < multiMeadow.getFlowers().size(); i++) {
            probabilitiesMap.put((1/multiMeadow.getFlowers().get(i).getPermutationValue())/sum + lastVal, multiMeadow.getFlowers().get(i));
            lastVal = (1/multiMeadow.getFlowers().get(i).getPermutationValue())/sum + lastVal;
        }
    }
}
