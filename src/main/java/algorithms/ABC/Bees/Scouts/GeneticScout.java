package algorithms.ABC.Bees.Scouts;

import algorithms.ABC.ArtificialBeeColony;
import algorithms.ABC.Flower;
import main.Loader;
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

    public void PMXCrossbreeding(Flower flower1, Flower flower2) {
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

        flower1.setPermutation(arr1);
        flower2.setPermutation(arr2);
    }

    /*public static void main(String[] args) {
        String distanceFilePath = System.getProperty("user.home") + "\\IdeaProjects\\metaheurystyka\\src\\main\\java\\data\\symetric\\berlin52.xml";
        String coordsFilePath = System.getProperty("user.home") + "\\IdeaProjects\\metaheurystyka\\src\\main\\java\\data\\symetric\\coords\\berlin52.tsp";
        TSPData data = Loader.loadWithCoords(distanceFilePath, coordsFilePath);

        int[] a = {0,1,2,3,4,5,6,7};
        int[] b = {7,6,3,4,1,0,2,5};
        Flower flower1 = new Flower(a, data);
        Flower flower2 = new Flower(b, data);

        PMXCrossbreeding(flower1, flower2);
    }*/
}
