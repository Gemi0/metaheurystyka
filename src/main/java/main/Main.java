package main;

import java.io.File;

public class Main {
    public static void main(String[] args){
        File file = new File("C:\\Users\\Dominik\\Desktop\\studia\\4 sem\\meta\\berlin52.xml");
        createMatrix(file.getAbsolutePath(),5);
        printMatrix(file);
    }

    private static void createMatrix(String path, int n) {
        MatrixBuilder matrixBuilder = new MatrixBuilder();
        matrixBuilder.create(path,n, "ASYMETRIC");
    }

    private static void printMatrix(File file) {
        double distance[][] = FileParser.parseFile(file);

        //Kwadratowa tablica
        for(int i = 0; i < distance.length; i++) {
            for(int j = 0; j < distance.length; j++) {
                System.out.print(Math.round(distance[i][j]) + ";");
            }
            System.out.println();
        }
    }
}

