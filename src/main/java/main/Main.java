package main;

import algorithms.KRandom;
import algorithms.Neighbour;
import algorithms.NeighbourExtended;

public class Main {

    static TSPData data;

    public static void main(String[] args){
        String distanceFilePath = "C:\\Users\\Dominik\\IdeaProjects\\metaheurystyka\\src\\main\\java\\data\\berlin52.xml";
        String coordsFilePath = "C:\\Users\\Dominik\\IdeaProjects\\metaheurystyka\\src\\main\\java\\data\\dsj1000.tsp";
        data = Loader.loadWithCoords(distanceFilePath, coordsFilePath);
        //data.printDistances();
        //data.printCoords();
        System.out.println(data.distance.length);
        Neighbour neighbour = new Neighbour();
        Tour tour1 = neighbour.neighbour(data, 0);
        System.out.println(tour1.length(data));
        KRandom kRandom = new KRandom();
        Tour tour = kRandom.kRandom(data, 100000);
        System.out.println(tour.length(data));
        NeighbourExtended neighbourExtended = new NeighbourExtended();
        Tour tour2 = neighbourExtended.neighbourExtended(data);
        System.out.println(tour2.length(data));
    }
}

