package algorithms.ABC.Bees.Scouts;

import algorithms.ABC.ArtificialBeeColony;
import algorithms.ABC.Flower;
import algorithms.Utils;
import algorithms.arrayTabu.neighborhoodBrowser.Util;
import main.TSPData;

import java.util.ArrayList;
import java.util.Random;

public abstract class Scouts {

    protected final int maxCounter;

    public Scouts(int maxCounter) {
        this.maxCounter = maxCounter;
    }

    protected Random rnd = new Random();

    public abstract ArrayList<Flower> sendBees(ArrayList<Flower> flowers,TSPData data);

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

            if (ArtificialBeeColony.bestFlower == null || flower.getPermutationValue() < ArtificialBeeColony.bestFlower.getPermutationValue()) {
                ArtificialBeeColony.bestFlower = flower;
            }
        }
        return meadow;
    }

    protected void randomFlower(Flower flower) {
        int[] currentPermutation = flower.getPermutation();
        int[] newPermutation = flower.getPermutation().clone();
        Util.invert(currentPermutation, newPermutation, rnd.nextInt(currentPermutation.length), rnd.nextInt(currentPermutation.length));
        flower.setPermutation(newPermutation);
    }
}
