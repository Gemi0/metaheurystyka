package algorithms.ABC;

import algorithms.ABC.Bees.Employees.Employees;
import algorithms.ABC.Bees.Employees.RandomEmployee;
import algorithms.ABC.Bees.Onlookers.Onlookers;
import algorithms.ABC.Bees.Onlookers.StochasticRoulette;
import algorithms.ABC.Bees.Scouts.GeneticScout;
import algorithms.ABC.Bees.Scouts.RandomScout;
import algorithms.ABC.Bees.Scouts.Scouts;
import algorithms.arrayTabu.stopConditions.StopCondition;
import main.TSPData;

import java.util.ArrayList;

public class ArtificialBeeColony {

    public static ArrayList<Flower> meadow;
    public static Flower bestFlower = null;

    public static int[] beeColony(StopCondition condition, TSPData data, int flowersNumber, int maxCounter) {
        Employees employees = new RandomEmployee();
        Scouts scouts = new RandomScout(maxCounter);
        Onlookers onlookers = new StochasticRoulette();

        meadow = scouts.startingMeadow(data, flowersNumber);

        long algorithmIteration = 0;
        long startTime = System.nanoTime();
        do {
            meadow = employees.sendBees(meadow , data);

            meadow  = onlookers.sendBees(meadow, data);

            meadow  = scouts.sendBees(meadow, data);
            //System.out.println(bestFlower.getPermutationValue());
        } while (!condition.shouldStop(algorithmIteration, System.nanoTime() - startTime));

        return bestFlower.getPermutation();
    }
}
