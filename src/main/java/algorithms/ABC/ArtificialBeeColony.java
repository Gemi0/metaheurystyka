package algorithms.ABC;

import algorithms.ABC.Bees.Bee;
import algorithms.ABC.Bees.Employees.SimpleEmployee;
import algorithms.ABC.Bees.Onlookers.Onlookers;
import algorithms.ABC.Bees.Onlookers.RouletteOnlooker;
import algorithms.ABC.Bees.Onlookers.StochasticRoulette;
import algorithms.ABC.Bees.Scouts.Scouts;
import algorithms.arrayTabu.stopConditions.StopCondition;
import main.TSPData;

import java.util.ArrayList;

public class ArtificialBeeColony {

    public static ArrayList<Flower> meadow;

    public static int[] beeColony(StopCondition condition, TSPData data, int flowersNumber, int maxCounter) {
        SimpleEmployee simpleEmployee = new SimpleEmployee();
        Scouts scouts = new Scouts(maxCounter);
        Onlookers onlookers = new StochasticRoulette();

        meadow = scouts.startingMeadow(data, flowersNumber);


        long algorithmIteration = 0;
        long startTime = System.nanoTime();
        do {
            meadow = simpleEmployee.sendBees(meadow, Bee.bestFlower , data);

            meadow  = onlookers.sendBees(meadow, Bee.bestFlower, data);

            meadow  = scouts.sendBees(meadow, Bee.bestFlower, data);
            System.out.println(Bee.bestFlower.getPermutationValue());
        } while (!condition.shouldStop(algorithmIteration, System.nanoTime() - startTime));

        return Bee.bestFlower.getPermutation();
    }
}
