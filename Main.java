import java.io.File;

public class Main {
    public static void main(String[] args){
        File file = new File("C:\\Users\\Admin\\Desktop\\dsj1000.xml");
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
