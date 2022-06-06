package algorithms.ABC.Bees.Onlookers;


import algorithms.ABC.Flower;
import main.TSPData;

import java.util.ArrayList;
import java.util.Random;

public abstract class Onlookers  {

    public abstract ArrayList<Flower> sendBees(ArrayList<Flower> flowers, TSPData data);

    protected Random rnd = new Random();

}