package main;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {
    static DistanceMatrix dm = new DistanceMatrix();

    public static void main(String[] args){
        File file = new File("C:\\Users\\Dominik\\Desktop\\meta\\burma14.xml");
        //createMatrix(file.getAbsolutePath(),5);
        setMatrix(file);
        //printMatrix();
        //printLength();
        KRandom kRandom = new KRandom();
        Tour tour = kRandom.kRandom(dm, 100000);
        System.out.println("Tu" +tour.length(dm));
    }

    private static void printLength() {
        Tour tour = new Tour();
        double length = tour.length(dm);
        System.out.println(length);
    }

    private static void setMatrix(File file) {
        dm.setMatrix(file);
    }

    private static void createMatrix(String path, int n) {
        MatrixBuilder matrixBuilder = new MatrixBuilder();
        matrixBuilder.create(path,n, "ASYMMETRIC");
    }

    private static void printMatrix() {

        //Kwadratowa tablica
        for(int i = 0; i < dm.getMatrixSize(); i++) {
            for(int j = 0; j < dm.getMatrixSize(); j++) {
                System.out.print(Math.round(dm.get(i,j)) + ";");
            }
            System.out.println();
        }
    }
}

