package algorithms;

import main.TSPData;

import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;


public class TabuSearch {

    static final int tabuListSize = 7;

    public static int[] tabuSearch(TSPData data, int[] startPermutation) {
        int[] currentPermutation = Arrays.copyOf(startPermutation, startPermutation.length);

        double bestPermutationValue = Utils.routeLength(currentPermutation, data);
        int[] bestPermutation = currentPermutation;
        TabuList<Solution> tabuList = new TabuList<>(tabuListSize);

        int withoutUpgrade = 0;

        while (withoutUpgrade < 1000) {
            Solution bestSolution = null;
            for (int i = 0; i < data.distance.length; i++) {
                for (int j = i + 1; j < data.distance.length; j++) {
                    int[] newPermutation = invert(currentPermutation, i, j);
                    double newPermutationValue = Utils.routeLength(newPermutation, data);
                    Solution solution = new Solution(i,j);

                    if (!tabuList.isTabu(solution)) {
                        if (newPermutationValue < bestPermutationValue) {
                            bestSolution = solution;
                            bestPermutationValue = newPermutationValue;
                            bestPermutation = newPermutation;
                            withoutUpgrade = 0;
                        }
                    }

                }
            }
            if (bestSolution != null) {
                if (tabuList.size() == tabuListSize) {
                    tabuList.poll();
                }
                tabuList.offer(bestSolution);
            }
            else {
                withoutUpgrade++;
            }
            currentPermutation = bestPermutation;
        }

        return currentPermutation;
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

    private static class TabuList<T> extends ArrayBlockingQueue<T> {

        public TabuList(int capacity) {
            super(capacity);
            this.elements = new LinkedList<>();
        }

        private final HashSet<T> hashSet = new HashSet<>();

        private final LinkedList<T> elements;

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
