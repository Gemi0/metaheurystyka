package algorithms.ABC.Bees.Employees;

import algorithms.ABC.ArtificialBeeColony;
import algorithms.ABC.Bees.Bee;
import algorithms.ABC.Flower;
import algorithms.arrayTabu.neighborhoodBrowser.Util;
import main.TSPData;

import java.util.ArrayList;

public class SimpleEmployee extends Bee {

    @Override
    public ArrayList<Flower> sendBees(ArrayList<Flower> flowers, TSPData data) {
        ArrayList<Flower> result = new ArrayList<>();
        for(Flower f : flowers) {
            int[] currentPermutation = f.getPermutation();
            int[] newPermutation = f.getPermutation().clone();
            Util.invert(currentPermutation, newPermutation,rnd.nextInt(currentPermutation.length), rnd.nextInt(currentPermutation.length));
            Flower newFlower = new Flower(newPermutation, data);

            if (newFlower.getPermutationValue() < f.getPermutationValue()) {
                result.add(newFlower);
                if (newFlower.getPermutationValue() < ArtificialBeeColony.bestFlower.getPermutationValue()) {
                    ArtificialBeeColony.bestFlower = newFlower;
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
