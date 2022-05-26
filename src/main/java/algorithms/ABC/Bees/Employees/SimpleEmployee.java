package algorithms.ABC.Bees.Employees;

import algorithms.ABC.Bees.Bee;
import algorithms.ABC.Flower;
import algorithms.arrayTabu.neighborhoodBrowser.Util;
import main.TSPData;

import java.util.ArrayList;

public class SimpleEmployee extends Bee {

    @Override
    public ArrayList<Flower> sendBees(ArrayList<Flower> flowers, Flower bestFlower, TSPData data) {
        Bee.bestFlower = bestFlower;
        ArrayList<Flower> result = new ArrayList<>();
        for(Flower f : flowers) {
            int[] currentPermutation = f.getPermutation();
            int[] newPermutation = f.getPermutation().clone();
            Util.invert(currentPermutation, newPermutation,rnd.nextInt(currentPermutation.length), rnd.nextInt(currentPermutation.length));
            Flower newFlower = new Flower(newPermutation, data);

            if (newFlower.getPermutationValue() < f.getPermutationValue()) {
                result.add(newFlower);
                if (newFlower.getPermutationValue() < bestFlower.getPermutationValue()) {
                    Bee.bestFlower = newFlower;
                }
            }
            else {
                f.increaseCounter();
                result.add(f);
            }
        }
        return result;
    }
}
