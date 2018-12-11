package pl.pollub.nawigacjapollub.DijkstraAlgorithm;

import java.util.ArrayList;

public class Graph {

    private Node[] nodes;
    private int noOfNodes;
    private Edge[] edges;
    private int noOfEdges;

    public Graph(Edge[] edges) {
        this.edges = edges;

        this.noOfNodes = calculateNoOfNodes(edges);
        this.nodes = new Node[this.noOfNodes];

        for(int n = 0; n < this.noOfNodes; n++){
            this.nodes[n] = new Node();
        }

        this.noOfEdges = edges.length;

        for(int edgeToAdd = 0; edgeToAdd < this.noOfEdges; edgeToAdd++){
            this.nodes[edges[edgeToAdd].getFromNodeIndex()].getEdges().add(edges[edgeToAdd]);
            this.nodes[edges[edgeToAdd].getToNodeIndex()].getEdges().add(edges[edgeToAdd]);
        }
    }

    private int calculateNoOfNodes(Edge[] edges){
        int noOfNodes = 0;

        for(Edge e : edges) {
            if (e.getToNodeIndex() > noOfNodes) {
                noOfNodes = e.getToNodeIndex();
            }
            if (e.getFromNodeIndex() > noOfNodes) {
                noOfNodes = e.getFromNodeIndex();
            }
        }

        noOfNodes++;
        return noOfNodes;
    }

    public void calculateShortestDistances() {
        this.nodes[0].setDistanceFromSource(0);
        int nextNode = 0;

        for(int i = 0; i < this.nodes.length; i++) {
            ArrayList<Edge> currentNodeEdges = this.nodes[nextNode].getEdges();
            for(int joinedEdge = 0; joinedEdge < currentNodeEdges.size(); joinedEdge++){
                int neighbourIndex = currentNodeEdges.get(joinedEdge).getNeighbourIndex(nextNode);

                if(!this.nodes[neighbourIndex]. isVisited()) {
                    int tentative = this.nodes[nextNode].getDistanceFromSource() + currentNodeEdges.get(joinedEdge).getLenght();

                    if(tentative < nodes[neighbourIndex].getDistanceFromSource()){
                        nodes[neighbourIndex].setDistanceFromSource(tentative);
                    }
                }
            }
        }
        nodes[nextNode].setVisited(true);
        nextNode = getNodeShortestDistanced();
    }

    public int getNodeShortestDistanced(){
        int storedNodeIndex = 0;
        int storedDist = Integer.MAX_VALUE;

        for(int i = 0; i < this.nodes.length; i++){
            int currentDist = this.nodes[i].getDistanceFromSource();

            if(!this.nodes[i].isVisited() && currentDist < storedDist){
                storedDist = currentDist;
                storedNodeIndex = i;
            }
        }
        return storedNodeIndex;
    }

}
