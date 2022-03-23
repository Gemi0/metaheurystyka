package algorithms;

import main.TSPData;
import main.Tour;

import java.util.stream.IntStream;

public class TwoOpt {
    public static Tour twoOpt(TSPData data){
        int currentPermutation[] = new int[data.distance.length];
        for(int i = 0; i < data.distance.length; i++) {
            currentPermutation[i] = i;
        }
        double currentPermutationValue = routeLength(currentPermutation, data);

        double bestPermutationValue = currentPermutationValue;
        int bestPermutation[] = currentPermutation;

        while(true) {
            for(int i = 0; i < data.distance.length; i++) {
                for(int j = i+1; j < data.distance.length; j++) {
                    int newPermutation[] = invert(currentPermutation, i, j);
                    double newPermutationValue = routeLength(newPermutation, data);

                    if(newPermutationValue < bestPermutationValue) {
                        bestPermutationValue = newPermutationValue;
                        bestPermutation = newPermutation;
                    }
                }
            }

            if(bestPermutationValue >= currentPermutationValue) {
                break;
            }
            currentPermutation = bestPermutation;
            currentPermutationValue = bestPermutationValue;
        }

        Tour tour = new Tour();
        for(int a : currentPermutation) {
            tour.add(a);
        }
        return tour;
    }

    private static int[] invert(int[] currentPermutation, int i, int j) {
        int[] temp = new int[currentPermutation.length];
        for(int x = 0; x < i; x++) {
            temp[x] = currentPermutation[x];
        }
        for(int x = i; x <= j;x++) {
            temp[x] = currentPermutation[j-x+i];
        }
        for(int x = j + 1; x < currentPermutation.length; x++) {
            temp[x] = currentPermutation[x];
        }
        return temp;
    }

    private static double routeLength(int[] route, TSPData data) {
        double length = 0;
        if(route.length < 2)
            return length;
        for(int i = 0; i < route.length - 1; i++){
            length += data.distance[route[i]][route[i+1]];
        }
        length += data.distance[route[data.distance.length - 1]][route[0]];
        return length;
    }

    private static double updateLength(double currentPermutationValue, int[] currentPermutation, int i, int j, TSPData data) {
        if(data.distance.length < 2)
            return 0;

        if(i != 0 && j != (data.distance.length - 1)) {
            return currentPermutationValue - data.distance[currentPermutation[i - 1]][currentPermutation[i]] + data.distance[currentPermutation[i - 1]][currentPermutation[j]] - data.distance[currentPermutation[j]][currentPermutation[j + 1]] + data.distance[currentPermutation[i]][currentPermutation[j + 1]];
        }
        if(i != 0 && j == (data.distance.length - 1)) {
            return currentPermutationValue - data.distance[currentPermutation[i - 1]][currentPermutation[i]] + data.distance[currentPermutation[i - 1]][currentPermutation[j]] - data.distance[currentPermutation[j]][currentPermutation[0]] + data.distance[currentPermutation[i]][currentPermutation[0]];
        }
        if(i == 0 && j != (data.distance.length - 1)) {
            return currentPermutationValue - data.distance[currentPermutation[data.distance.length - 1]][currentPermutation[i]] + data.distance[currentPermutation[data.distance.length - 1]][currentPermutation[j]] - data.distance[currentPermutation[j]][currentPermutation[j + 1]] + data.distance[currentPermutation[i]][currentPermutation[j + 1]];
        }
        return currentPermutationValue;
    }

    public static Tour acceleratedTwoOpt(TSPData data){
        int currentPermutation[] = new int[data.distance.length];
        for(int i = 0; i < data.distance.length; i++) {
            currentPermutation[i] = i;
        }
        double currentPermutationValue = routeLength(currentPermutation, data);

        double bestPermutationValue = currentPermutationValue;
        int bestPermutation[] = currentPermutation;

        while(true) {
            for(int i = 0; i < data.distance.length; i++) {
                for(int j = i+1; j < data.distance.length; j++) {
                    int newPermutation[] = invert(currentPermutation, i, j);
                    double newPermutationValue = updateLength(currentPermutationValue, currentPermutation, i, j, data);
                    if(newPermutationValue < bestPermutationValue) {
                        bestPermutationValue = newPermutationValue;
                        bestPermutation = newPermutation;
                    }
                }
            }

            if(bestPermutationValue >= currentPermutationValue) {
                break;
            }
            currentPermutation = bestPermutation;
            currentPermutationValue = bestPermutationValue;
        }

        Tour tour = new Tour();
        for(int a : currentPermutation) {
            tour.add(a);
        }
        return tour;
    }
}
