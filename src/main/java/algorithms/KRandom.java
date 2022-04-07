package algorithms;

import main.TSPData;

import java.util.*;

public class KRandom {

    public static int[] kRandom(TSPData data, int k) {
        int[] best = null;

        int[] permutation = new int[data.distance.length];
        for (int i = 0; i < permutation.length; i++) {
            permutation[i] = i;
        }

        for (int i = 0; i <= k; i++) {
            Utils.shuffle(permutation);

            if (best == null || Utils.routeLength(permutation, data) < Utils.routeLength(best, data)) {
                best = Arrays.copyOf(permutation, permutation.length);
            }
        }
        return best;
    }

}
