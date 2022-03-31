package algorithms;

import main.TSPData;
import main.Tour;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class KRandom {
    /**
     * Generating the shortest tour using k random tours and picking the shortest.
     * @param data distance matrix
     * @param k number of tours generated
     * @return shortest tour generated
     */
    public Tour kRandom(TSPData data, int k) { //O(k) * O(n) = O(n)
        Random rand = new Random();
        int n = data.distance.length;
        Tour best = null;
        for (int a =0; a <=k; a++) { // O(k)
            List<Integer> arr = new ArrayList<>(n);
            for (int i = 0; i < n; i++) {
                arr.add(i);
            }

            int r; // stores random number
            int tmp;

            //shuffle above input array
            for (int i = n; i > 0; i--) { //O(n)
                r = rand.nextInt(i);

                tmp = arr.get(i - 1);
                arr.set(i - 1, arr.get(r));
                arr.set(r, tmp);
            }
            Tour tour = new Tour();
            tour.setList(arr);
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
