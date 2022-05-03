package main;

import algorithms.*;
import algorithms.arrayTabu.Tabu;
import algorithms.arrayTabu.neighborhoodBrowser.singlethreaded.accelerated.AcceleratedInvertBrowser;
import algorithms.arrayTabu.neighborhoodBrowser.singlethreaded.basic.InvertBrowser;
import algorithms.arrayTabu.stopConditions.IterationStopCondition;

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

        /*System.out.println("Sample run");
        System.out.println("Distance: " + distanceFilePath);
        System.out.println("Coords: " + coordsFilePath);
        System.out.println();
        System.out.println("10000-Random: " + Utils.routeLength(KRandom.kRandom(data, 10000), data));
        System.out.println("Neighbor: " + Utils.routeLength(Neighbor.neighbor(data, 0), data));
        System.out.println("NeighborExtended: " + Utils.routeLength(NeighborExtended.neighborExtended(data), data));
        System.out.println("2OPT: " + Utils.routeLength(TwoOpt.twoOpt(data, startPermutation), data));
        System.out.println("Accelerated2OPT: " + Utils.routeLength(TwoOpt.acceleratedTwoOpt(data, startPermutation), data));
        System.out.println("NeighborExtended->2OPT: " + Utils.routeLength(TwoOpt.twoOpt(data, NeighborExtended.neighborExtended(data)), data));
        System.out.println("KRandom->TabuSearch: " + Utils.routeLength(TabuSearch.tabuSearch(data, KRandom.kRandom(data, 10000)), data));
        System.out.println("NeighbourExtended->TabuSearch: " + Utils.routeLength(TabuSearch.tabuSearch(data, NeighborExtended.neighborExtended(data)), data));

        //ArrayTabu
        System.out.println("ArrayTabuInvert: " + Utils.routeLength(BasicTabu.tabuSearchInvert(startPermutation, data, 100, 2000000000L), data));
        System.out.println("ArrayTabuSwap: " + Utils.routeLength(BasicTabu.tabuSearchSwap(startPermutation, data, 100, 2000000000L), data));
        System.out.println("ArrayTabuInsert: " + Utils.routeLength(BasicTabu.tabuSearchInsert(startPermutation, data, 100, 2000000000L), data));
*/
        //ArrayTabuAccelerated
        //System.out.println("ArrayTabuInvertAcc: " + Utils.routeLength(BasicTabu.tabuSearchInvertAcc(startPermutation, data, 100, 2000000000L), data));
        //System.out.println("ArrayTabuSwapAcc: " + Utils.routeLength(BasicTabu.tabuSearchSwapAcc(startPermutation, data, 100, 1200000000L), data));
        //System.out.println("ArrayTabuInsertAcc: " + Utils.routeLength(BasicTabu.tabuSearchInsertAcc(startPermutation, data, 100, 2000000000L), data));

        //System.out.println("ArrayTabuInvert: " + Utils.routeLength(BasicTabu.tabuSearchInvert(startPermutation, data, 100, 200000000L), data));
        //System.out.println("ArrayTabuInvert: " + Utils.routeLength(BasicTabu.tabuSearch(new InvertMultithreadedBrowser(), startPermutation, data, 100, 200000000L), data));
        for(int i = 1; i <= 100; i++) {
            System.out.println("\n" + i);
            System.out.println("ArrayTabuInvertBasic(stagnation enabled): " + Utils.routeLength(Tabu.tabuSearch(new AcceleratedInvertBrowser(), new IterationStopCondition(5000), startPermutation, data, i, false, 100), data));
            System.out.println("ArrayTabuInvertBasic(stagnation disabled): " + Utils.routeLength(Tabu.tabuSearch(new AcceleratedInvertBrowser(), new IterationStopCondition(5000), startPermutation, data, i, false, Long.MAX_VALUE), data));
        }
    }
}

