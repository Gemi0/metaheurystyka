package main;

import java.io.File;

public class Main {

    public static void main(String[] args){
        String distanceFilePath = "C:\\Users\\Admin\\IdeaProjects\\metaheurystyka\\src\\main\\java\\data\\dsj1000.xml";
        String coordsFilePath = "C:\\Users\\Admin\\IdeaProjects\\metaheurystyka\\src\\main\\java\\data\\dsj1000.tsp";
        TSPData data = Loader.loadWithCoords(distanceFilePath, coordsFilePath);
        data.printDistances();
        data.printCoords();
    }

}

