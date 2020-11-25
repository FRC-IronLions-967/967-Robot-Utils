package frc.team967.navigation;

import java.util.concurrent.atomic.AtomicInteger;

public class Node {

    private static AtomicInteger numNodes = new AtomicInteger();

    // stores the x position of this node on the field
    private double x;

    // stores the y position of this node on the field
    private double y;

    // stores the distance between this node and all nodes it has a direct connection
    // all nodes that this does not have a connection to should be initialized to 0
    private int[] neighborDist;

    public Node(double x, double y, int[] neighborDist) {
        numNodes.incrementAndGet();

        this.x = x;
        this.y = y;

        this.neighborDist = neighborDist;
    }

    // standard getter methods for the values in this class
    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public int[] getNeighborDist() {
        return neighborDist;
    }

    public static int getNumNodes() {
        return numNodes.get();
    }
}