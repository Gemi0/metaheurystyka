package main;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class KRandom {
    public Tour kRandom(DistanceMatrix dm, int k) {
        Random rand = new Random();
        int n = dm.getMatrixSize();
        Tour best = null;
        for (int a =0; a <=k; a++) {
            List<Integer> arr = new ArrayList<>(n);
            for (int i = 0; i < n; i++) {
                arr.add(i);
            }

            int r; // stores random number
            int tmp;

            //shuffle above input array
            for (int i = n; i > 0; i--) {
                r = rand.nextInt(i);

                tmp = arr.get(i - 1);
                arr.set(i - 1, arr.get(r));
                arr.set(r, tmp);
            }
            System.out.println(arr);
            Tour tour = new Tour();
            tour.setList(arr);
            //System.out.println(tour.length(dm));
            if (best == null) {
                best = tour;
            }
            else if (best.length(dm) > tour.length(dm)) {
                best = tour;
            }
        }
        return best;
    }
}
