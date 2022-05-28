package algorithms.ABC.Bees.Employees;


import algorithms.ABC.Flower;
import main.TSPData;

import java.util.ArrayList;
import java.util.Random;

public abstract class Employees {

    public abstract ArrayList<Flower> sendBees(ArrayList<Flower> flowers, TSPData data);

    protected final Random rnd = new Random();
}
