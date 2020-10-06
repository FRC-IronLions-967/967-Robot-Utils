package frc.team967.navigation;

public class Dijkstra {
    //A test file to experiment with Dijkstra and see if it would be a good way of doing autonomous
    private static final int INFINITE = Integer.MAX_VALUE;
    private int numNodes;
    private int graph[][];
    private int dist[];
    private int parents[];
    private Boolean verticesChecked[];

    public Dijkstra(int graph[][], int numNodes) {
        this.numNodes = numNodes;
        this.graph = graph;

        dist = new int[numNodes];
        parents = new int[numNodes];
        verticesChecked = new Boolean[numNodes];

        for (int i = 0; i < numNodes; i++) {
            dist[i] = INFINITE;
            verticesChecked[i] = false;
        }
    }

    //standard convenience getter methods for external use to get the algorithm outputs and debugging

    //returns the shortest path to the node that the algorithm was told to find
    public int[] getDist() {
        return dist;
    }

    public int[] getParents() {
        return parents;
    }

    public int[][] getGraph() {
        return graph;
    }

    //standard convenience setter methods to alter values to run the algorithm again without creating a new object
    //TODO
    public void changeGraph(int newGraph[][], int numNodes) throws Exception {
        if(newGraph[0].length != newGraph.length) {
            throw new Exception();
        } else {
            this.numNodes = graph.length;
            this.graph = newGraph;
            this.dist = new int[numNodes];
            this.parents = new int[numNodes];
        }
    }

    //convenience class method to find the next smallest distance that has not yet been used by the algorithm 
    private int minDistance() {
        int min = INFINITE;
        int min_index = -1; 
  
        for (int i = 0; i < numNodes; i++) {
            if (verticesChecked[i] == false && dist[i] <= min) { 
                min = dist[i]; 
                min_index = i; 
            } 
        }

        return min_index; 
    }

    //method to run Dijkstra's Shortest Path Algorithm on the current object
    public void runAlg(int src) {
        if(graph[0].length != graph.length || graph.length != numNodes) return; //check for bad conditions

        for (int i = 0; i < numNodes; i++) { //defensive code in case the object's graph is updated and the vertices have thus not been checked
            dist[i] = INFINITE;
            verticesChecked[i] = false;
        }

        dist[src] = 0;
        parents[src] = -1;

        for(int i = 0; i < numNodes - 1; i++) {
            int x = minDistance(); //find next closest node
            verticesChecked[x] = true;

            for(int y = 0; y < numNodes; y++) {
                if (!verticesChecked[y] && graph[x][y] != 0 && dist[x] != INFINITE && dist[x] + graph[x][y] < dist[y]) {
                    dist[y] = dist[x] + graph[x][y];
                    parents[y] = x;
                }
            }
        }
    }


}