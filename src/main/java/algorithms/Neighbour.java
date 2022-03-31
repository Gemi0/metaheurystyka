package algorithms;

import main.TSPData;
import main.Tour;

import java.util.ArrayList;
import java.util.List;

public class Neighbour {
    public Tour neighbour(TSPData data, int start) { //O(n)*O(n) = O(n^2)
        int n = data.distance.length;
        List<Integer> arr = new ArrayList<>(n);
        for (int i = 0; i < n; i++) { //O(n)
            arr.add(i);
        }
        List<Integer> tourArr = new ArrayList<>(n);
        tourArr.add(start);
        for (int i = 0; i < n; i++) { //O(n)
            double min = 99999999;
            int minPos = -1;
            for (int j : arr) { //O(n)
                if (data.distance[start][j] < min && data.distance[start][j] != -1) {
                    min = data.distance[start][j];
                    minPos = j;
                }
            }
            if (minPos != -1) {
                start = minPos;
                tourArr.add(minPos);
                arr.remove(Integer.valueOf(minPos));
            }
        }
        Tour tour = new Tour();
        tour.setList(tourArr);
        return tour;
    }
}
