package algorithms.ABC.Bees;

import algorithms.ABC.Flower;
import main.TSPData;

import java.util.ArrayList;
import java.util.Random;

public abstract class Bee {

    protected Random rnd = new Random();

    public abstract ArrayList<Flower> sendBees(ArrayList<Flower> flowers,TSPData data);
}
