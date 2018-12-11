package pl.pollub.nawigacjapollub.DijkstraAlgorithm;

public class Edge {

    private int fromNodeIndex;
    private int toNodeIndex;
    private int lenght;

    public Edge(int fromNodeIndex, int toNodeIndex, int lenght) {
        this.fromNodeIndex = fromNodeIndex;
        this.toNodeIndex = toNodeIndex;
        this.lenght = lenght;
    }

    public int getFromNodeIndex() {
        return fromNodeIndex;
    }

    public int getToNodeIndex() {
        return toNodeIndex;
    }

    public int getLenght() {
        return lenght;
    }

    public int getNeighbourIndex(int nodeIndex){
        if(this.fromNodeIndex == nodeIndex) {
            return this.toNodeIndex;
        } else {
            return this.fromNodeIndex;
        }
    }
}
