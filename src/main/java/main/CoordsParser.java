package main;

import java.awt.geom.Point2D;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Locale;
import java.util.Scanner;

public class CoordsParser {
    public static Point2D.Double[] parseFile(File file) {
        Scanner scanner = null;
        try {
            scanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
        String line;

        int size = 0;

        while(scanner.hasNextLine())  {
            line = scanner.nextLine();
            line = line.toLowerCase(Locale.ROOT);
            if(line.startsWith("dimension")) {
                line = line.replace("dimension", "").replace(":","");
                try {
                    size = Integer.parseInt(line.trim());
                    break;
                } catch (NumberFormatException exception){
                    exception.printStackTrace();
                    return null;
                }
            }
        }
        while(scanner.hasNextLine()) {
            line = scanner.nextLine();
            line = line.toUpperCase();
            if(line.trim().equals("NODE_COORD_SECTION")) {
                break;
            }
        }

        Point2D.Double coords[] = new Point2D.Double[size];

        while(scanner.hasNextInt()) {
            int index;
            double coord_x, coord_y;
            index = scanner.nextInt() - 1;
            coord_x = scanner.nextDouble();
            coord_y = scanner.nextDouble();
            coords[index] = new Point2D.Double(coord_x, coord_y);
        }

        return coords;
    }
}
