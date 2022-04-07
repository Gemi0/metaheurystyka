package algorithms;

import main.TSPData;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Utils {
    public static double routeLength(int[] route, TSPData data) {
        double length = 0;
        if (route.length < 2)
            return length;
        for (int i = 0; i < route.length - 1; i++) {
            length += data.distance[route[i]][route[i + 1]];
        }
        int x = route[data.distance.length - 1];
        length += data.distance[x][route[0]];
        return length;
    }

    public static void shuffle(int[] array) {
        Random random = ThreadLocalRandom.current();
        for (int i = array.length - 1; i > 0; i--) {
            int index = random.nextInt(i + 1);
            int tmp = array[index];
            array[index] = array[i];
            array[i] = tmp;
        }
    }

}
