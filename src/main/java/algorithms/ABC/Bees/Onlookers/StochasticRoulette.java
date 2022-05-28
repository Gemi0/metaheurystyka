package algorithms.ABC.Bees.Onlookers;

import algorithms.ABC.ArtificialBeeColony;
import algorithms.ABC.Bees.Employees.RandomEmployee;
import algorithms.ABC.Flower;
import main.TSPData;

import java.util.ArrayList;

public class StochasticRoulette extends Onlookers {

    @Override
    public ArrayList<Flower> sendBees(ArrayList<Flower> flowers, TSPData data) {
        RandomEmployee employee = new RandomEmployee();
        ArrayList<Flower> result = new ArrayList<>();
        while (result.size() < flowers.size()) {
            Flower flower = flowers.get(rnd.nextInt(flowers.size()));
            if ((1/flower.getPermutationValue())/(1/ArtificialBeeColony.bestFlower.getPermutationValue()) >= rnd.nextDouble()) {
                result.add(flower);
            }
        }
        result = employee.sendBees(result, data);
        return result;
    }
}
