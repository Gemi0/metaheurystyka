package algorithms.ABC.Bees;

import algorithms.ABC.Flower;
import main.TSPData;

import java.util.ArrayList;
import java.util.Random;

public abstract class Bee {

    protected Random rnd = new Random();

    protected static Flower bestFlower;

    public Flower getBestFlower() {
        return bestFlower;
    }

    public abstract ArrayList<Flower> sendBees(ArrayList<Flower> flowers, Flower bestFlower, TSPData data);
}
