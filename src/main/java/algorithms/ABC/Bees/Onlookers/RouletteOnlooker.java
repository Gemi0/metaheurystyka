package algorithms.ABC.Bees.Onlookers;

import algorithms.ABC.Bees.Bee;
import algorithms.ABC.Bees.Employees.SimpleEmployee;
import algorithms.ABC.Flower;
import main.TSPData;

import java.util.ArrayList;
import java.util.TreeMap;

public class RouletteOnlooker extends Bee {

    TreeMap<Double, Flower> probabilitiesMap = new TreeMap<Double, Flower>() {};

    private void calculateProbabilities(ArrayList<Flower> flowers) {
        double sum =0;
        for (Flower f : flowers) {
            sum += 1 / f.getPermutationValue();
        }
        double lastVal = (1/flowers.get(0).getPermutationValue())/sum;
        probabilitiesMap.put(lastVal, flowers.get(0));
        for (int i = 1; i < flowers.size(); i++) {
            probabilitiesMap.put((1/flowers.get(i).getPermutationValue())/sum + lastVal, flowers.get(i));
            lastVal = (1/flowers.get(i).getPermutationValue())/sum + lastVal;
        }
    }


    @Override
    public ArrayList<Flower> sendBees(ArrayList<Flower> flowers, Flower bestFlower, TSPData data) {
        calculateProbabilities(flowers);
        SimpleEmployee employee = new SimpleEmployee();
        ArrayList<Flower> result = new ArrayList<>();
        for (int i = 0; i< flowers.size(); i++) {
            double r = rnd.nextDouble();
            double key;
            try {
                key = probabilitiesMap.floorKey(r);
            }
            catch (NullPointerException e ) {
                key = probabilitiesMap.ceilingKey(0.0);
            }

            result.add(probabilitiesMap.get(key));
        }
        result = employee.sendBees(result, bestFlower, data);
        super.bestFlower = employee.getBestFlower();
        return result;
    }
}
