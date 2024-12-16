package com.study.datastructure.graph;

/**
 * <h3>边</h3>
 */
public class Edge {

    public Vertex linked;
    public int weight;

    public Edge(Vertex linked) {
        this(linked, 1);
    }

    public Edge(Vertex linked, int weight) {
        this.linked = linked;
        this.weight = weight;
    }
}
