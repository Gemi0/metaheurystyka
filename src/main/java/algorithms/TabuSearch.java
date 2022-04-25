package algorithms;

import main.TSPData;

import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;

public class TabuSearch {

    static final int stopVariable = 1000;
    static final int tabuListSize = 7;
    static final int maxStagnation = 100;
    static int withoutUpgrade;
    static int iteration;
    static long start;
    static long end;

    private static boolean iterationsWithoutUpgradeStop(int maxIterations) {
        return withoutUpgrade < maxIterations;
    }

    private static boolean iterationsStop(int maxIterations) {
        return iteration < maxIterations;
    }

    private static boolean timeStop(int maxTime) {
        return end - start < (long)maxTime;
    }

    public static int[] tabuSearch(TSPData data, int[] startPermutation) {
        int[] currentPermutation = Arrays.copyOf(startPermutation, startPermutation.length);
        double bestPermutationValue = Utils.routeLength(currentPermutation, data);

        int[] bestPermutation = currentPermutation;
        int[] neighbourhoodBestPermutation = currentPermutation;

        TabuList<Solution> tabuList = new TabuList<>(tabuListSize);
        BacktrackList<Solution> backtrackList = new BacktrackList<>(101);
        TabuList<Solution> stagnationTabuList = new TabuList<>(tabuListSize);

        double neighbourhoodBestPermutationValue;
        withoutUpgrade = 0;
        iteration = 0;

        start = System.currentTimeMillis();
        while (timeStop(stopVariable)) {
            Solution bestSolution = null;
            neighbourhoodBestPermutationValue = Double.MAX_VALUE;
            for (int i = 0; i < data.distance.length; i++) {
                for (int j = i + 1; j < data.distance.length; j++) {
                    int[] newPermutation = invert(currentPermutation, i, j);
                    double newPermutationValue = Utils.routeLength(newPermutation, data);
                    Solution solution = new Solution(i,j);

                    if (!tabuList.isTabu(solution)) {
                        if (newPermutationValue < neighbourhoodBestPermutationValue) {
                            bestSolution = solution;
                            neighbourhoodBestPermutationValue = newPermutationValue;
                            neighbourhoodBestPermutation = newPermutation;
                        }
                    }
                    else if (newPermutationValue < bestPermutationValue) { //aspiration
                        bestSolution = solution;
                        neighbourhoodBestPermutationValue = newPermutationValue;
                        neighbourhoodBestPermutation = newPermutation;
                    }
                }
            }
            if (bestSolution != null) {
                backtrackList.offer(bestSolution);
                if (tabuList.size() == tabuListSize) {
                    tabuList.poll();
                }
                tabuList.offer(bestSolution);

            }
            if (neighbourhoodBestPermutationValue < bestPermutationValue) {
                bestPermutationValue = neighbourhoodBestPermutationValue;
                bestPermutation = neighbourhoodBestPermutation;
                withoutUpgrade = 0;
            }
            else {
                withoutUpgrade++;
            }
            if (withoutUpgrade < 8) {
                stagnationTabuList.offer(bestSolution);
            }

            currentPermutation = neighbourhoodBestPermutation;
            if (withoutUpgrade == maxStagnation) { //anti - stagnation
                currentPermutation = bestPermutation;
                tabuList.elements = stagnationTabuList.elements;
            }
            /*if (withoutUpgrade == maxStagnation) { //Backtracking that goes back to the best solution. Possible change to go just few steps back
                Solution solution;
                TabuList<Solution> tempTabuList = new TabuList<>(tabuListSize);
                while (Arrays.equals(currentPermutation, bestPermutation)) {
                    solution = backtrackList.poll();
                    tempTabuList.offer(solution);
                    currentPermutation = invert(currentPermutation, solution.x, solution.y);
                }
                for(int i = 0; i < tempTabuList.size(); i++) {
                    tabuList.offer(tempTabuList.poll());
                }
            }*/
            iteration++;
            end = System.currentTimeMillis();
        }
        return bestPermutation;
    }

    private static int[] invert(int[] currentPermutation, int i, int j) {
        int[] temp = new int[currentPermutation.length];
        if (i >= 0) System.arraycopy(currentPermutation, 0, temp, 0, i);
        for (int x = i; x <= j; x++) {
            temp[x] = currentPermutation[j - x + i];
        }
        if (currentPermutation.length - (j + 1) >= 0)
            System.arraycopy(currentPermutation, j + 1, temp, j + 1, currentPermutation.length - (j + 1));
        return temp;
    }

    private static int[] swap(int[] currentPermutation, int i, int j) {
        int[] temp = currentPermutation.clone();
        int tempElem = temp[i];
        temp[i] = temp[j];
        temp[j] = tempElem;
        return temp;
    }

    private static int[] insert(int[] currentPermutation, int i, int j) {
        int[] temp = currentPermutation.clone();
        int tempElem = temp[i];
        if (j - i >= 0) System.arraycopy(temp, i + 1, temp, i, j - i);
        temp[j] = tempElem;
        return temp;
    }

    private static class BacktrackList<T> extends ArrayBlockingQueue<T> {

        private final LinkedList<T> elements;

        public BacktrackList(int capacity) {
            super(capacity);
            this.elements = new LinkedList<>();
        }

        @Override
        public Iterator<T> iterator() {
            return elements.iterator();
        }

        @Override
        public int size() {
            return elements.size();
        }

        @Override
        public boolean offer(T t) {
            if(t == null) return false;
            elements.add(t);
            return true;
        }

        @Override
        public T poll() {
            Iterator<T> iter = elements.iterator();
            T t = iter.next();
            T t2 = t;
            while(true){
                t2 = t;
                try {
                    t = iter.next();
                }
                catch (NoSuchElementException e ) {
                    iter.remove();
                    return t2;
                }
            }
        }

        @Override
        public T peek() {
            return elements.getFirst();
        }
    }

    private static class TabuList<T> extends ArrayBlockingQueue<T> {

        public TabuList(int capacity) {
            super(capacity);
            this.elements = new LinkedList<>();
        }

        private final HashSet<T> hashSet = new HashSet<>();

        private LinkedList<T> elements;

        public boolean isTabu(T t) {
            return hashSet.contains(t);
        }

        @Override
        public Iterator<T> iterator() {
            return elements.iterator();
        }

        @Override
        public int size() {
            return elements.size();
        }

        @Override
        public boolean offer(T t) {
            if(t == null) return false;
            hashSet.add(t);
            elements.add(t);
            return true;
        }

        @Override
        public T poll() {
            Iterator<T> iter = elements.iterator();
            T t = iter.next();
            if(t != null){
                hashSet.remove(iter);
                iter.remove();
                return t;
            }
            return null;
        }

        @Override
        public T peek() {
            return elements.getFirst();
        }
    }

    private static class Solution {
        public final int x;
        public final int y;

        public Solution(final int x, final int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            Solution other = (Solution) obj;
            return this.x == other.x && this.y == other.y;
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + x;
            result = prime * result + y;
            return result;
        }
    }
}
