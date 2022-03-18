package main;

import java.util.ArrayList;
import java.util.List;

public class Tour {

    private List<Integer> nodes = new ArrayList<>();

    public void setList(List<Integer> list) {
        nodes = list;
    }

    public void add(int node) {
        nodes.add(node);
    }

    public int get(int node) {
        return nodes.get(node);
    }

    public void set(int node, int value) {
        nodes.set(node, value);
    }

    public double length(TSPData data) {

        int first = -1;
        int previous = -1;
        double length = 0;
        for (Integer node : nodes) {
            if (first == -1) {
                first = node;
                previous = node;
            }
            else {
                length += data.distance[previous][node];
                previous = node;
            }
        }
        length += data.distance[previous][first];
        return length;
    }
}
