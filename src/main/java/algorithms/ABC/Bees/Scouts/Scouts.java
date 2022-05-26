package algorithms.ABC.Bees.Scouts;

import algorithms.ABC.Bees.Bee;
import algorithms.ABC.Flower;
import algorithms.Utils;
import algorithms.arrayTabu.neighborhoodBrowser.Util;
import main.TSPData;

import java.util.ArrayList;

public class Scouts extends Bee {

    private final int maxCounter;

    public Scouts(int maxCounter) {
        this.maxCounter = maxCounter;
    }

    public ArrayList<Flower> startingMeadow(TSPData data, int k) {
        ArrayList<Flower> meadow = new ArrayList<>();

        int[] permutation = new int[data.distance.length];
        for (int i = 0; i < permutation.length; i++) {
            permutation[i] = i;
        }

        for (int i = 0; i <= k; i++) {
            Utils.shuffle(permutation);
            Flower flower = new Flower(permutation, data);
            meadow.add(flower);

            if (bestFlower == null || flower.getPermutationValue() < bestFlower.getPermutationValue()) {
                bestFlower = flower;
            }
        }
        return meadow;
    }

    @Override
    public ArrayList<Flower> sendBees(ArrayList<Flower> flowers, Flower bestFlower, TSPData data) {
        for (int i = 0; i < flowers.size(); i++) {
            if (flowers.get(i).getCounter() > maxCounter) {
                int[] currentPermutation = flowers.get(i).getPermutation();
                int[] newPermutation = flowers.get(i).getPermutation().clone();
                Util.invert(currentPermutation, newPermutation,rnd.nextInt(currentPermutation.length), rnd.nextInt(currentPermutation.length));
                Flower newFlower = new Flower(newPermutation, data);
                flowers.set(i, newFlower);

                if (newFlower.getPermutationValue() < bestFlower.getPermutationValue()) {
                    super.bestFlower = newFlower;
                }
            }
        }
        return flowers;
    }
}
