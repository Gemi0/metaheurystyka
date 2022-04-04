package algorithms;

import main.TSPData;

public class NeighborExtended {
    public static int[] neighborExtended(TSPData data) {
        int[] best = null;
        for (int i = 0; i < data.distance.length; i++) {
            int[] permutation = Neighbor.neighbor(data, i);
            if (best == null || Utils.routeLength(permutation, data) < Utils.routeLength(best, data)) {
                best = permutation;
            }
        }
        return best;
    }
}
