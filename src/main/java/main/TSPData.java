package main;

import java.awt.geom.Point2D;

public class TSPData {
    public final double[][] distance;
    public final Point2D.Double[] coords;

    public TSPData(double[][] distance) {
        this.distance = distance;
        coords = null;
    }

    public TSPData(double[][] distance, Point2D.Double[] coords) {
        this.distance = distance;
        this.coords = coords;
    }

    public void printDistances() {
        for(int i = 0; i < distance.length; i++) {
            for(int j = 0; j < distance.length; j++) {
                System.out.print(Math.round(distance[i][j]) + ";");
            }
            System.out.println();
        }
    }

    public void printCoords() {
        for(int i = 0; i < coords.length; i++) {
            System.out.println(i+":"+coords[i].getX()+","+coords[i].getY());
        }
    }
}
