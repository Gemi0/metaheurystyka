package algorithms;

import main.TSPData;
import main.Tour;

public class NeighbourExtended {
    Neighbour neighbour = new Neighbour();
    Tour best = null;
    public Tour neighbourExtended(TSPData data) {
        for (int a = 0; a < data.distance.length; a++) {
            Tour tour = neighbour.neighbour(data,a);
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
