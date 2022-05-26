package algorithms.ABC;

import algorithms.ABC.Bees.Employees.SimpleEmployee;
import algorithms.ABC.Bees.Onlookers.RouletteOnlooker;
import algorithms.ABC.Bees.Scouts.Scouts;
import algorithms.arrayTabu.stopConditions.StopCondition;
import main.TSPData;

import java.util.ArrayList;

public class ArtificialBeeColony {

    public static ArrayList<Flower> meadow;

    public static int[] beeColony(StopCondition condition, TSPData data, int flowersNumber, int maxCounter) {
        SimpleEmployee simpleEmployee = new SimpleEmployee();
        //Employees employees = new InvertMultithreadedEmployee();
        Scouts scouts = new Scouts(maxCounter);
        RouletteOnlooker onlookers = new RouletteOnlooker();

        meadow = scouts.startingMeadow(data, flowersNumber);
        Flower bestFlower = scouts.getBestFlower();

        //employees.prepareMemory(bestPermutation, data);

        long algorithmIteration = 0;
        long startTime = System.nanoTime();
        do {
            meadow = simpleEmployee.sendBees(meadow, bestFlower , data);
            bestFlower = simpleEmployee.getBestFlower();

            meadow  = onlookers.sendBees(meadow, bestFlower, data);
            bestFlower = onlookers.getBestFlower();

            meadow  = scouts.sendBees(meadow, bestFlower, data);
            bestFlower = scouts.getBestFlower();

        } while (!condition.shouldStop(algorithmIteration, System.nanoTime() - startTime));

        return bestFlower.getPermutation();
    }
}
