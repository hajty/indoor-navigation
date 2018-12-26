package pl.pollub.nawigacjapollub.DijkstraAlgorithm;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import java.util.ArrayList;

import pl.pollub.nawigacjapollub.PointsContract;
import pl.pollub.nawigacjapollub.PointsDbHelper;

public class Graph
{
    private Context context;
    private Node[] nodes;
    private int numberOfNodes;
    private Edge[] edges;
    private int numberOfEdges;

    public Graph(Context context)
    {
        this.context = context;
        this.initializeEdges();

        this.numberOfNodes = this.calculateNumberOfNodes(this.edges);
        this.nodes = new Node[this.numberOfNodes];

        for (int i = 0; i < this.numberOfNodes; i++)
            this.nodes[i] = new Node();

        this.numberOfEdges = this.edges.length;

        for (int edgeToAdd = 0; edgeToAdd < this.numberOfEdges; edgeToAdd++)
        {
            this.nodes[edges[edgeToAdd].getFromNodeId()].getEdges().add(this.edges[edgeToAdd]);
            this.nodes[edges[edgeToAdd].getToNodeId()].getEdges().add(this.edges[edgeToAdd]);
        }
    }

    // załadowanie krawędzi z bazy
    private void initializeEdges()
    {
        PointsDbHelper db = new PointsDbHelper(this.context);
        Cursor cursor = db.getAllEdges();
        int i = 0;

        this.edges = new Edge[cursor.getCount()];

        while(cursor.moveToNext())
        {
            this.edges[i] = new Edge(
                    cursor.getInt(cursor.getColumnIndexOrThrow(PointsContract.EdgesEntry.COLUMN_NAME_FROM_POINT_ID)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(PointsContract.EdgesEntry.COLUMN_NAME_TO_POINT_ID)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(PointsContract.EdgesEntry.COLUMN_NAME_LENGTH))
            );
            i++;
        }
    }

    // obliczenie liczby wezlow na podstawie krawedzi
    private int calculateNumberOfNodes(Edge[] edges)
    {
        int numberOfNodes = 0;

        for (Edge edge : edges)
        {
            if (edge.getToNodeId() > numberOfNodes)
                numberOfNodes = edge.getToNodeId();

            if (edge.getFromNodeId() > numberOfNodes)
                numberOfNodes = edge.getFromNodeId();
        }
        numberOfNodes++;

        return numberOfNodes;
    }

    private int getNodeShortestDistanced()
    {
        int storedNodeIndex = 0;
        int storedDist = Integer.MAX_VALUE;

        for (int i = 0; i < this.nodes.length; i++)
        {
            int currentDist = this.nodes[i].getDistanceFromSource();

            if (!this.nodes[i].isVisited() && currentDist < storedDist)
            {
                storedDist = currentDist;
                storedNodeIndex = i;
            }
        }

        return storedNodeIndex;
    }

    public void calculateShortestDistances()
    {
        this.nodes[1].setDistanceFromSource(0);
        int nextNode = 0;

        for (int i = 0; i < this.nodes.length; i++)
        {
            ArrayList<Edge> currentNodeEdges = this.nodes[nextNode].getEdges();

            for (int joinedEdge = 0; joinedEdge < currentNodeEdges.size(); joinedEdge++)
            {
                int neighbourId = currentNodeEdges.get(joinedEdge).getNeighbourId(nextNode);

                if (!this.nodes[neighbourId].isVisited())
                {
                    int tentative = this.nodes[nextNode].getDistanceFromSource() + currentNodeEdges.get(joinedEdge).getLength();

                    if (tentative < nodes[neighbourId].getDistanceFromSource())
                    {
                        nodes[neighbourId].setDistanceFromSource(tentative);
                    }
                }
            }

            nodes[nextNode].setVisited(true);
            nextNode = this.getNodeShortestDistanced();
        }
    }

    public ArrayList<Integer> calculateShortestPath(int startPoint, int finishPoint, boolean mode)
    {
        ArrayList<Integer> tempPath = new ArrayList<>();
        ArrayList<Integer> path = new ArrayList<>();

        // Jeśli algorytm jest ustawiony z trybem inwalidy, zablokuj klatki schodowe bez windy
        if (mode)
        {
            this.edges[8].setLength(9999);
            this.edges[17].setLength(9999);
        }

        this.nodes[startPoint].setDistanceFromSource(0);
        int nextNode = 0;
        int fromNode = finishPoint;

        for (int i = 0; i < this.nodes.length; i++)
        {
            ArrayList<Edge> currentNodeEdges = this.nodes[nextNode].getEdges();
            for (int joinedEdge = 0; joinedEdge < currentNodeEdges.size(); joinedEdge++)
            {
                int neighbourId = currentNodeEdges.get(joinedEdge).getNeighbourId(nextNode);

                if (!this.nodes[neighbourId].isVisited())
                {
                    int tentative = this.nodes[nextNode].getDistanceFromSource() + currentNodeEdges.get(joinedEdge).getLength();

                    if (tentative < nodes[neighbourId].getDistanceFromSource())
                    {
                        nodes[neighbourId].setDistanceFromSource(tentative);

                        if (neighbourId > currentNodeEdges.get(joinedEdge).getFromNodeId())
                            nodes[neighbourId].setFromNodeId(currentNodeEdges.get(joinedEdge).getFromNodeId());
                        else
                            nodes[neighbourId].setFromNodeId(currentNodeEdges.get(joinedEdge).getToNodeId());
                    }
                }
            }

            nodes[nextNode].setVisited(true);
            nextNode = this.getNodeShortestDistanced();
        }

        // wypisanie wezlow od tylu
        tempPath.add(finishPoint);
        while (fromNode != startPoint)
        {
            tempPath.add(this.nodes[fromNode].getFromNodeId());
            fromNode = this.nodes[fromNode].getFromNodeId();
        }

        // przepisanie odwroconej tablicy
        for (int i = tempPath.size()-1; i >= 0; i--)
        {
            path.add(tempPath.get(i));
        }

        return path;
    }

    public void printResult() {
//        this.calculateShortestDistances();
        String output = "\nNumber of nodes = " + this.numberOfNodes;
        output += "\nNumber of edges = " + this.numberOfEdges;

        StringBuilder output2 = new StringBuilder();

        for (int i = 0; i < this.nodes.length; i++) {
            output += ("\nThe shortest distance to node " + i + " is " + nodes[i].getDistanceFromSource());
        }

        ArrayList<Integer> path = this.calculateShortestPath(3, 18, false);

        for (int i = 0; i < path.size(); i++)
        {
            output2.append(path.get(i));
            output2.append(" ");
        }

        Log.i("DIJIKSTRA", output );
        Log.i("DIJIKSTRA 2", output2.toString());
    }

    public Node[] getNodes()
    {
        return nodes;
    }

    public int getNumberOfNodes()
    {
        return numberOfNodes;
    }

    public Edge[] getEdges()
    {
        return edges;
    }

    public int getNumberOfEdges()
    {
        return numberOfEdges;
    }
}