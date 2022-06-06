package algorithms.arrayTabu.neighborhoodBrowser;

import main.TSPData;

public class Util {
    public static void swap(int[] currentPermutation, int[] newPermutation, int i, int j) {
        System.arraycopy(currentPermutation, 0, newPermutation, 0, currentPermutation.length);
        int temp = newPermutation[i];
        newPermutation[i] = newPermutation[j];
        newPermutation[j] = temp;
    }
    public static void invert(int[] currentPermutation, int[] newPermutation, int i, int j) {
        if (i >= 0)
            System.arraycopy(currentPermutation, 0, newPermutation, 0, i);
        for (int x = i; x <= j; x++) {
            newPermutation[x] = currentPermutation[j - x + i];
        }
        if (currentPermutation.length - (j + 1) >= 0)
            System.arraycopy(currentPermutation, j + 1, newPermutation, j + 1, currentPermutation.length - (j + 1));
    }
    public static void insert(int[] currentPermutation, int[] newPermutation, int i, int j) {
        if (i < j) {
            System.arraycopy(currentPermutation, 0, newPermutation, 0, i);
            System.arraycopy(currentPermutation, i + 1, newPermutation, i, j - i);
            newPermutation[j] = currentPermutation[i];
            if (currentPermutation.length - (j + 1) >= 0)
                System.arraycopy(currentPermutation, j + 1, newPermutation, j + 1, currentPermutation.length - (j + 1));
            return;
        }
        if (i > j) {
            System.arraycopy(currentPermutation, 0, newPermutation, 0, j);
            newPermutation[j] = currentPermutation[i];
            System.arraycopy(currentPermutation, j, newPermutation, j + 1, i - j);
            if (currentPermutation.length - (i + 1) >= 0)
                System.arraycopy(currentPermutation, i + 1, newPermutation, i + 1, currentPermutation.length - (i + 1));
            return;
        }
        System.arraycopy(currentPermutation, 0, newPermutation, 0, currentPermutation.length);
    }
    public static double updateInvertValue(int[] currentPermutation, TSPData data, double currentPermutationValue, int i, int j){
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
        //return Math.ceil(retVal);
    }
    public static double updateSwapValue(int[] currentPermutation, TSPData data, double currentPermutationValue, int i, int j) {
        if (data.distance.length < 2)
            return 0;

        if(i == j || currentPermutation.length == 2)
            return currentPermutationValue;

        int x = Math.min(i, j);
        int y = Math.max(i, j);

        if(x == 0 && y == currentPermutation.length - 1){
            currentPermutationValue -= data.distance[currentPermutation[x]][currentPermutation[x+1]] + data.distance[currentPermutation[y-1]][currentPermutation[y]] + data.distance[currentPermutation[y]][currentPermutation[x]];
            currentPermutationValue += data.distance[currentPermutation[y]][currentPermutation[x+1]] + data.distance[currentPermutation[y-1]][currentPermutation[x]] + data.distance[currentPermutation[x]][currentPermutation[y]];
            return Math.ceil(currentPermutationValue);
        }

        if(y - x == 1) {
            currentPermutationValue -= (data.distance[currentPermutation[(x - 1 + currentPermutation.length) % currentPermutation.length]][currentPermutation[x]]
                    + data.distance[currentPermutation[x]][currentPermutation[y]]
                    + data.distance[currentPermutation[y]][currentPermutation[(y + 1) % currentPermutation.length]]);
            currentPermutationValue += (data.distance[currentPermutation[(x - 1 + currentPermutation.length) % currentPermutation.length]][currentPermutation[y]]
                    + data.distance[currentPermutation[y]][currentPermutation[x]]
                    + data.distance[currentPermutation[x]][currentPermutation[(y + 1) % currentPermutation.length]]);
            return Math.ceil(currentPermutationValue);
        }


        currentPermutationValue -= (data.distance[currentPermutation[(i - 1 + currentPermutation.length) % currentPermutation.length]][currentPermutation[i]]
                + data.distance[currentPermutation[i]][currentPermutation[(i + 1) % currentPermutation.length]]
                + data.distance[currentPermutation[(j - 1 + currentPermutation.length) % currentPermutation.length]][currentPermutation[j]]
                + data.distance[currentPermutation[j]][currentPermutation[(j + 1) % currentPermutation.length]]);
        currentPermutationValue += (data.distance[currentPermutation[(i - 1 + currentPermutation.length) % currentPermutation.length]][currentPermutation[j]]
                + data.distance[currentPermutation[j]][currentPermutation[(i + 1) % currentPermutation.length]]
                + data.distance[currentPermutation[(j - 1 + currentPermutation.length) % currentPermutation.length]][currentPermutation[i]]
                + data.distance[currentPermutation[i]][currentPermutation[(j + 1) % currentPermutation.length]]);
        return Math.round(currentPermutationValue * 10000) / 10000.0;
        //return Math.ceil(currentPermutationValue);
    }
}
