package main;

import java.util.ArrayList;
import java.util.List;

public class Tour {

    private final List<Integer> nodes = new ArrayList<>();

    public void add(int node) {
        nodes.add(node);
    }

    public double length(DistanceMatrix dm) {
        nodes.add(0);
        nodes.add(1);
        nodes.add(2);
        nodes.add(3);
        nodes.add(4);

        int first = -1;
        int previous = -1;
        double length = 0;
        for (Integer node : nodes) {
            if (first == -1) {
                first = node;
                previous = node;
            }
            else {
                length += dm.get(previous,node);
                previous = node;
            }
        }
        length += dm.get(previous,first);
        return length;
    }
}
