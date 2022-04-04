package algorithms;

import main.TSPData;

import java.util.HashSet;
import java.util.Set;

public class Neighbor {

    public static int[] neighbor(TSPData data, int start) {
        Set<Integer> set = new HashSet<>(data.distance.length);
        for (int i = 0; i < data.distance.length; i++) {
            set.add(i);
        }

        int[] permutation = new int[data.distance.length];
        permutation[0] = start;
        set.remove(start);

        int from = start;
        int index = 1;

        while (!set.isEmpty()) {
            double minDistance = Double.MAX_VALUE;
            int minPosition = -1;
            for (int to : set) {
                if (data.distance[from][to] < minDistance) {
                    minDistance = data.distance[from][to];
                    minPosition = to;
                }
            }
            set.remove(minPosition);
            permutation[index] = minPosition;
            from = minPosition;
            index++;
        }

        return permutation;
    }
}
