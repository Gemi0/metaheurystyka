package algorithms.ABC.Bees.Scouts;

import algorithms.ABC.ArtificialBeeColony;
import algorithms.ABC.Flower;
import algorithms.Utils;
import algorithms.arrayTabu.neighborhoodBrowser.Util;
import main.TSPData;

import java.util.ArrayList;
import java.util.Random;

public class RandomScout extends Scouts {

    public RandomScout(int max) {
        super(max);
    }

    @Override
    public ArrayList<Flower> sendBees(ArrayList<Flower> flowers, TSPData data) {
        for (Flower flower : flowers) {
            if (flower.getCounter() > maxCounter && flower.getPermutationValue() > ArtificialBeeColony.bestFlower.getPermutationValue()) {
                randomFlower(flower);
                if (flower.getPermutationValue() < ArtificialBeeColony.bestFlower.getPermutationValue()) {
                    ArtificialBeeColony.bestFlower = flower;
                }
            }
        }
        return flowers;
    }
}
