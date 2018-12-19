package pl.pollub.nawigacjapollub.DijkstraAlgorithm;

public class Edge
{
    private int fromNodeId;
    private int toNodeId;
    private int length;

    public Edge(int fromNodeId, int toNodeId, int length)
    {
        this.setFromNodeId(fromNodeId);
        this.setToNodeId(toNodeId);
        this.setLength(length);
    }

    public int getFromNodeId()
    {
        return fromNodeId;
    }

    public void setFromNodeId(int fromNodeId)
    {
        this.fromNodeId = fromNodeId;
    }

    public int getToNodeId()
    {
        return toNodeId;
    }

    public void setToNodeId(int toNodeId)
    {
        this.toNodeId = toNodeId;
    }

    public int getLength()
    {
        return length;
    }

    public void setLength(int length)
    {
        this.length = length;
    }

    public int getNeighbourId(int nodeId)
    {
        if (this.fromNodeId == nodeId)
            return this.getToNodeId();
        else
            return this.getFromNodeId();
    }
}
