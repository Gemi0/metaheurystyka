package algorithms.ABC.Bees.Scouts;

import algorithms.ABC.ArtificialBeeColony;
import algorithms.ABC.Flower;
import main.TSPData;

import java.util.*;

public class GeneticScout extends Scouts{

    public GeneticScout(int max) {
        super(max);
    }

    double probability = 0.5;

    @Override
    public ArrayList<Flower> sendBees(ArrayList<Flower> flowers, TSPData data) {
        int flowerToBreedIndex = -1;
        Flower flower;
        for (int i = 0; i <flowers.size(); i++) {
            flower = flowers.get(i);
            if (flower.getCounter() > maxCounter && flower.getPermutationValue() > ArtificialBeeColony.bestFlower.getPermutationValue()) {
                if (flowerToBreedIndex == -1) {
                    if (rnd.nextDouble() >= probability) {
                        flowerToBreedIndex = i;
                    }
                    else {
                        randomFlower(flower);
                    }
                }
                else {
                    if (rnd.nextDouble() >= probability) {
                        PMXCrossbreeding(flower, flowers.get(flowerToBreedIndex));
                        flowerToBreedIndex = -1;
                    }
                    else {
                        randomFlower(flower);
                    }
                }
                if (flower.getPermutationValue() < ArtificialBeeColony.bestFlower.getPermutationValue()) {
                    ArtificialBeeColony.bestFlower = flower;
                }
            }
        }
        return flowers;
    }

    public static void PMXCrossbreeding(Flower flower1, Flower flower2) {
        int[] flower1Arr = flower1.getPermutation();
        int[] flower2Arr = flower2.getPermutation();
        Integer[] flower2Copy = Arrays.stream(flower2Arr).boxed().toArray( Integer[]::new );
        Integer[] flower1Copy = Arrays.stream(flower1Arr).boxed().toArray( Integer[]::new );
        Map<Integer, Integer> map1 = new TreeMap<>();
        Map<Integer, Integer> map2 = new TreeMap<>();
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
            if(count[arr1[i]] == 0) {
                count[arr1[i]]++;
            }
            else {
                Optional<Integer> firstKey = map1.keySet().stream().findFirst();
                if (firstKey.isPresent()) {
                    Integer key = firstKey.get();
                    arr1[i] = map1.get(key);
                    map1.remove(key);
                }
            }

            if(count2[arr2[i]] == 0) {
                count2[arr2[i]]++;
            }
            else {
                Optional<Integer> firstKey = map2.keySet().stream().findFirst();
                if (firstKey.isPresent()) {
                    Integer key = firstKey.get();
                    arr2[i] = map2.get(key);
                    map2.remove(key);
                }
            }
        }
        flower1.setPermutation(arr1);
        flower2.setPermutation(arr2);
    }
}
