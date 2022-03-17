package main;

import java.io.File;

public class Loader {
    public static TSPData loadWithCoords(String distanceFilePath, String coordsFilePath) {
        File distanceFile = new File(distanceFilePath);
        File coordsFile = new File(coordsFilePath);
        return new TSPData(XMLParser.parseFile(distanceFile), CoordsParser.parseFile(coordsFile));
    }

    public static TSPData loadWithoutCoords(String distanceFilePath) {
        File distanceFile = new File(distanceFilePath);
        return new TSPData(XMLParser.parseFile(distanceFile));
    }
}
