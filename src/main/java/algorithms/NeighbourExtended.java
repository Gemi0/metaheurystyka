package algorithms;

import main.TSPData;
import main.Tour;

public class NeighbourExtended {
    public static Tour neighbourExtended(TSPData data) {
        Tour best = null;
        for (int a = 0; a < data.distance.length; a++) {
            Tour tour = Neighbour.neighbour(data,a);
            if (best == null) {
                best = tour;
            }
            else if (best.length(data) > tour.length(data)) {
                best = tour;
            }
        }
        return best;
    }
}
