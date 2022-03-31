package main;

import java.awt.geom.Point2D;

public class TSPGenerator {
    public static TSPData generateWithoutCoords(double minDistance, double maxDistance, int nodes, boolean symetric) {
        double distances[][] = new double[nodes][nodes];
        if(!symetric) {
            for(int i = 0; i< distances.length;i++) {
                for(int j = 0; j < distances.length; j++) {
                    distances[i][j] = minDistance + (maxDistance - minDistance) * Math.random();
                }
            }
        } else {
            for(int i = 0; i < distances.length; i++) {
                for(int j = 0; j <= i; j++) {
                    distances[i][j] = distances[j][i] = minDistance + (maxDistance - minDistance) * Math.random();
                }
            }
        }
        for(int i = 0; i < distances.length; i++){
            distances[i][i] = -1;
        }
        return new TSPData(distances);
    }

    public static TSPData generateWithCoords(double boundingBoxSide, int nodes) {
        double distances[][] = new double[nodes][nodes];
        Point2D.Double coords[] = new Point2D.Double[nodes];

        for(int i = 0; i < distances.length; i++) {
            coords[i] = new Point2D.Double((Math.random()-0.5)*boundingBoxSide*2, (Math.random()-0.5)*boundingBoxSide*2);
        }

        for(int i = 0; i < distances.length; i++) {
            for(int j = 0; j <= i; j++) {
                distances[i][j] = distances[j][i] = coords[i].distance(coords[j]);
            }
        }

        for(int i = 0; i < distances.length; i++){
            distances[i][i] = -1;
        }

        return new TSPData(distances, coords);
    }
}
