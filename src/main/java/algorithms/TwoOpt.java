package algorithms;

import main.TSPData;

import java.util.Arrays;

public class TwoOpt {
    public static int[] twoOpt(TSPData data, int[] startPermutation) {
        int[] currentPermutation = Arrays.copyOf(startPermutation, startPermutation.length);
        double currentPermutationValue = Utils.routeLength(currentPermutation, data);

        double bestPermutationValue = currentPermutationValue;
        int[] bestPermutation = currentPermutation;

        while (true) {
            for (int i = 0; i < data.distance.length; i++) {
                for (int j = i + 1; j < data.distance.length; j++) {
                    int[] newPermutation = invert(currentPermutation, i, j);
                    double newPermutationValue = Utils.routeLength(newPermutation, data);

                    if (newPermutationValue < bestPermutationValue) {
                        bestPermutationValue = newPermutationValue;
                        bestPermutation = newPermutation;
                    }
                }
            }

            if (bestPermutationValue >= currentPermutationValue) {
                break;
            }
            currentPermutation = bestPermutation;
            currentPermutationValue = bestPermutationValue;
        }

        return currentPermutation;
    }

    private static int[] invert(int[] currentPermutation, int i, int j) {
        int[] temp = new int[currentPermutation.length];
        for (int x = 0; x < i; x++) {
            temp[x] = currentPermutation[x];
        }
        for (int x = i; x <= j; x++) {
            temp[x] = currentPermutation[j - x + i];
        }
        for (int x = j + 1; x < currentPermutation.length; x++) {
            temp[x] = currentPermutation[x];
        }
        return temp;
    }

    private static double updateLength(double currentPermutationValue, int[] currentPermutation, int i, int j, TSPData data) {
        if (data.distance.length < 2)
            return 0;

        double retVal = 0;
        if (i != 0 && j != (data.distance.length - 1)) {
            retVal = currentPermutationValue - data.distance[currentPermutation[i - 1]][currentPermutation[i]] + data.distance[currentPermutation[i - 1]][currentPermutation[j]] - data.distance[currentPermutation[j]][currentPermutation[j + 1]] + data.distance[currentPermutation[i]][currentPermutation[j + 1]];
        } else if (i != 0 && j == (data.distance.length - 1)) {
            retVal = currentPermutationValue - data.distance[currentPermutation[i - 1]][currentPermutation[i]] + data.distance[currentPermutation[i - 1]][currentPermutation[j]] - data.distance[currentPermutation[j]][currentPermutation[0]] + data.distance[currentPermutation[i]][currentPermutation[0]];
        } else if (i == 0 && j != (data.distance.length - 1)) {
            retVal = currentPermutationValue - data.distance[currentPermutation[data.distance.length - 1]][currentPermutation[i]] + data.distance[currentPermutation[data.distance.length - 1]][currentPermutation[j]] - data.distance[currentPermutation[j]][currentPermutation[j + 1]] + data.distance[currentPermutation[i]][currentPermutation[j + 1]];
        } else
            retVal = currentPermutationValue;
        return Math.round(retVal * 10000) / 10000.0;
    }

    public static int[] acceleratedTwoOpt(TSPData data, int[] startPermutation) {
        int[] currentPermutation = Arrays.copyOf(startPermutation, startPermutation.length);
        double currentPermutationValue = Utils.routeLength(currentPermutation, data);

        double bestPermutationValue = currentPermutationValue;
        int[] bestPermutation = currentPermutation;

        while (true) {
            for (int i = 0; i < data.distance.length; i++) {
                for (int j = i + 1; j < data.distance.length; j++) {
                    int[] newPermutation = invert(currentPermutation, i, j);
                    double newPermutationValue = updateLength(currentPermutationValue, currentPermutation, i, j, data);
                    if (newPermutationValue < bestPermutationValue) {
                        bestPermutationValue = newPermutationValue;
                        bestPermutation = newPermutation;
                    }
                }
            }

            if (bestPermutationValue >= currentPermutationValue) {
                break;
            }
            currentPermutation = bestPermutation;
            currentPermutationValue = bestPermutationValue;
        }

        return currentPermutation;
    }

}
