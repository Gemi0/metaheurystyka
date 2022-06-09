package algorithms.ABC.singleABC.scout;

import algorithms.ABC.ArtificialBeeColony;
import algorithms.Utils;
import algorithms.ABC.singleABC.SingleFlower;
import algorithms.ABC.singleABC.SingleMeadow;
import main.TSPData;

import java.util.*;

public class SingleBestGeneticScout extends SingleScout{


    private final Random rnd;
    private final double probability;

    public SingleBestGeneticScout(SingleMeadow singleMeadow, TSPData data, int maxRetries, double probability) {
        super(singleMeadow, data, maxRetries);
        this.probability = probability;
        this.rnd = new Random();
    }

    @Override
    public void processFlower(SingleFlower flower) {
        if (flower.getRetriesCounter() > maxRetries && flower != singleMeadow.getBestFlower() && rnd.nextDouble() <= probability) {
            PMXCrossbreeding(flower, singleMeadow.getBestFlower());
            if (flower.getPermutationValue() < singleMeadow.getBestFlower().getPermutationValue()) {
                singleMeadow.setBestFlower(flower);
            }
        }
    }

    private void PMXCrossbreeding(SingleFlower flower1, SingleFlower flower2) {
        int[] flower1Arr = flower1.getPermutation();
        int[] flower2Arr = flower2.getPermutation();
        Integer[] flower2Copy = Arrays.stream(flower2Arr).boxed().toArray( Integer[]::new );
        Integer[] flower1Copy = Arrays.stream(flower1Arr).boxed().toArray( Integer[]::new );
        Map<Integer, Integer> map1 = new TreeMap<>();
        Map<Integer, Integer> map2 = new TreeMap<>();
        Map<Integer, Integer> map1c = new TreeMap<>();
        Map<Integer, Integer> map2c = new TreeMap<>();
        Random rnd = new Random();

        int pivot1 = rnd.nextInt(flower1Arr.length - 2);
        int pivot2 = rnd.nextInt(pivot1 + 1, flower1Arr.length);
        int[] arr1 = new int[flower1Arr.length];
        int[] arr2 = new int[flower1Arr.length];

        System.arraycopy(flower1Arr, 0, arr1, 0, pivot1);
        System.arraycopy(flower2Arr, pivot1, arr1, pivot1, pivot2- pivot1);
        System.arraycopy(flower1Arr, pivot2, arr1, pivot2, flower1Arr.length-pivot2);

        System.arraycopy(flower2Arr, 0, arr2, 0, pivot1);
        System.arraycopy(flower1Arr, pivot1, arr2, pivot1, pivot2- pivot1);
        System.arraycopy(flower2Arr, pivot2, arr2, pivot2, flower1Arr.length-pivot2);

        int[] arr1copy = arr1.clone();
        Arrays.sort(arr1copy);
        int[] arr2copy = arr2.clone();
        Arrays.sort(arr2copy);

        for (int i = 0; i < arr1copy.length; i++) {
            int temp = Math.abs(arr1copy[i]);
            int temp2 = Math.abs(arr2copy[i]);

            if (arr1copy[temp] > 0)
                arr1copy[temp] = -arr1copy[temp];
            if (arr2copy[temp2] > 0)
                arr2copy[temp2] = -arr2copy[temp2];
        }
        for (int i = 0; i < arr1copy.length; i++) {
            if (arr1copy[i] > 0) {
                map1.put(Arrays.asList(flower2Copy).indexOf(i+1),i);
            }
            if (arr2copy[i] > 0) {
                map2.put(Arrays.asList(flower1Copy).indexOf(i+1),i);
            }

        }
        int[] count = new int[arr1.length];
        int[] count2 = new int[arr1.length];
        for(int i = 0; i < arr1.length; i++) {

            count[arr1[i]]++;
            if(count[arr1[i]] > 1) {
                Optional<Integer> firstKey = map1.keySet().stream().findFirst();
                if (i < pivot1 || i > pivot2) {
                    if (firstKey.isPresent()) {
                        count[arr1[i]]--;
                        Integer key = firstKey.get();
                        arr1[i] = map1.get(key);
                        map1.remove(key);
                    }
                }
                else {
                    if (firstKey.isPresent()) {
                        Integer key = firstKey.get();
                        int val = map1.get(key);
                        map1.remove(key);
                        map1c.put(key, val);
                    }
                }
            }


            count2[arr2[i]]++;
            if(count2[arr2[i]] > 1) {
                Optional<Integer> firstKey = map2.keySet().stream().findFirst();
                if (i < pivot1 || i > pivot2) {
                    if (firstKey.isPresent()) {
                        count2[arr2[i]]--;
                        Integer key = firstKey.get();
                        arr2[i] = map2.get(key);
                        map2.remove(key);
                    }
                }
                else {
                    if (firstKey.isPresent()) {
                        Integer key = firstKey.get();
                        int val = map2.get(key);
                        map2.remove(key);
                        map2c.put(key, val);
                    }
                }
            }
        }

        for(int i = 0; i < arr1.length; i++) {
            if(count[arr1[i]] > 1) {
                if (i < pivot1 || i > pivot2) {
                    Optional<Integer> firstKey = map1c.keySet().stream().findFirst();
                    if (firstKey.isPresent()) {
                        Integer key = firstKey.get();
                        arr1[i] = map1c.get(key);
                        map1c.remove(key);
                    }
                }
            }

            if(count2[arr2[i]] > 1) {
                if (i < pivot1 || i > pivot2) {
                    Optional<Integer> firstKey = map2c.keySet().stream().findFirst();
                    if (firstKey.isPresent()) {
                        Integer key = firstKey.get();
                        arr2[i] = map2c.get(key);
                        map2c.remove(key);
                    }
                }
            }
        }

        flower1.setPermutation(arr1, Utils.routeLength(arr1, data));
        //flower2.setPermutation(arr2, Utils.routeLength(arr2, data));
    }
}
