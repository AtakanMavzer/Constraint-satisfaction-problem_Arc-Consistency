
import java.io.*;
import java.util.*;

class Graph {
    private int V; // No. of vertices

    // Array of lists for
    // Adjacency List Representation
    private ArrayList<Integer> adj[];

    // domains stores a list of String values
    // will be manipulated as you enforce arc consistency
    public ArrayList<String> domains[];

    // Constructor
    Graph(int v) {
        this.V = v;
        this.adj = new ArrayList[v];
        this.domains = new ArrayList[v];

        for (int i = 0; i < v; ++i) {
            adj[i] = new ArrayList<Integer>();
            domains[i] = new ArrayList<String>();
            domains[i].add("Red");
            domains[i].add("Green");
            domains[i].add("Blue");
        }

    }

    // Function to add an edge into the graph
    void addEdge(int v, int w) {
        if (v >= V || w >= V || v == w)
            return; // invalid edge
        if (!adj[v].contains(w))
            adj[v].add(w); // Add w to v's list.
        if (!adj[w].contains(v))
            adj[w].add(v); // Since the edges are bi-directional, so add v to w's list
    }

    boolean isAdjacent(int v, int w) {
        return adj[v].contains(w);
    }

    void removeEdge(int v, int w) {
        if (v >= V || w >= V || v == w)
            return; // invalid edge

        if (adj[v].contains(w))
            adj[v].remove(Integer.valueOf(w));
        if (adj[w].contains(v))
            adj[w].remove(Integer.valueOf(v));
    }

    public void assignValue(int v, String value) {
        if (v >= V)
            return; // invalid vertex
        domains[v].clear();
        domains[v].add(value);
    }

    public boolean makeArcConsistent() {
        boolean result = true;
        for (int i = 0; i < this.adj.length; i++) {

            for (int j = 0; j < adj[i].size(); j++) {

                if (arc_reduce(i, adj[i].get(j))) {

                    result = true;

                } else if (this.domains[i].size() == 0) {
                    result = false;
                    System.out.println("FAILED");
                    break;
                }
            }
        }
        return result;
    }

    boolean arc_reduce(int i, int j) {
        boolean change = false;
        if (this.domains[i].size() == 1) {
            if (this.domains[j].size() > 0 && this.domains[j].contains(this.domains[i].get(0))) {
                this.domains[j].remove(domains[j].indexOf(this.domains[i].get(0)));
                change = true;
            }
        } else if (this.domains[j].size() == 1) {
            if (this.domains[i].size() > 0 && this.domains[j].contains(this.domains[i].get(0))) {
                this.domains[i].remove(domains[i].indexOf(this.domains[j].get(0)));
                change = true;
            } 
        }
        return change;
    }

    public String toString() {

        for (int i = 0; i < adj.length; i++) {
            // st += i + "\t" + adj[i] + "\n";
            System.out.print(i + "->");

            for (int x : adj[i]) {
                System.out.print(x + " ");
            }
            System.out.print("\t" + domains[i] + "\n");

        }
        return "";
    }
}

class MapColoring {

    public static void main(String args[]) {
        Graph g = new Graph(7); // Create a graph with 5 nodes
        // Add some edges in the graph
        g.addEdge(0, 1);
        g.addEdge(0, 2);
        g.addEdge(1, 3);
        g.addEdge(1, 2);
        g.addEdge(3, 2);
        g.addEdge(3, 4);
        g.addEdge(2, 3);
        g.addEdge(2, 5);
        g.addEdge(2, 4);
        g.addEdge(4, 5);
        g.addEdge(6, 2);

        g.assignValue(2, "Blue");
        g.assignValue(3, "Green");

        System.out.println(g);
        g.makeArcConsistent();
        System.out.println(g);
///////////////////////////////////////////////////////////////////////////////////////////////////////
        Graph failgraph = new Graph(7);
        failgraph.addEdge(0, 1);
        failgraph.addEdge(0, 2);
        failgraph.addEdge(1, 3);
        failgraph.addEdge(1, 2);
        failgraph.addEdge(3, 2);
        failgraph.addEdge(3, 4);
        failgraph.addEdge(2, 3);
        failgraph.addEdge(2, 5);
        failgraph.addEdge(2, 4);
        failgraph.addEdge(4, 5);
        failgraph.addEdge(6, 2);

        failgraph.assignValue(2, "Green");
        failgraph.assignValue(5, "Green");

        System.out.println(failgraph);
        failgraph.makeArcConsistent();
        System.out.println(failgraph);
    }

}
