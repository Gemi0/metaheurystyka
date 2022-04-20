package main;

import algorithms.*;

public class Main {

    public static void main(String[] args) {
        String distanceFilePath = System.getProperty("user.home") + "\\IdeaProjects\\metaheurystyka\\src\\main\\java\\data\\symetric\\berlin52.xml";
        String coordsFilePath = System.getProperty("user.home") + "\\IdeaProjects\\metaheurystyka\\src\\main\\java\\data\\symetric\\coords\\berlin52.tsp";
        TSPData data = Loader.loadWithCoords(distanceFilePath, coordsFilePath);
        System.out.println(data.distance.length);

        int[] startPermutation = new int[data.distance.length];
        for (int i = 0; i < data.distance.length; i++) {
            startPermutation[i] = i;
        }

        System.out.println("Sample run");
        System.out.println("Distance: " + distanceFilePath);
        System.out.println("Coords: " + coordsFilePath);
        System.out.println();
        System.out.println("10000-Random: " + Utils.routeLength(KRandom.kRandom(data, 10000), data));
        System.out.println("Neighbor: " + Utils.routeLength(Neighbor.neighbor(data, 0), data));
        System.out.println("NeighborExtended: " + Utils.routeLength(NeighborExtended.neighborExtended(data), data));
        System.out.println("2OPT: " + Utils.routeLength(TwoOpt.twoOpt(data, startPermutation), data));
        System.out.println("Accelerated2OPT: " + Utils.routeLength(TwoOpt.acceleratedTwoOpt(data, startPermutation), data));
        System.out.println("NeighborExtended->2OPT: " + Utils.routeLength(TwoOpt.twoOpt(data, NeighborExtended.neighborExtended(data)), data));
        System.out.println("KRandom->TabuSearch: " + Utils.routeLength(TabooSearch.tabooSearch(data, KRandom.kRandom(data,10000)), data) );
        System.out.println("NeighbourExtended->TabuSearch: " + Utils.routeLength(TabooSearch.tabooSearch(data, NeighborExtended.neighborExtended(data)), data) );
    }
}

