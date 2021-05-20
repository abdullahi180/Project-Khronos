package Services.PathFinding;


import Entity.Location;

public class Node implements Comparable<Node> {
    private double gCost; // distance from start node
    private double hCost; // distance from end node
    private int y;
    private int x;
    private boolean isWall;
    private Node previousNode;
    private Location location;

    /**
     * Represents a single pixel as a node in a graph.
     *
     * @param y coordinate
     * @param x coordinate
     */
    public Node(int y, int x) {
        this.y = y;
        this.x = x;
    }

    public Node(int y, int x, Location location) {
        this(y, x);
        this.location = location;
    }

    public double calculateEuclideanDistance(Node finishNode) {
        int xDif = Math.abs(y - finishNode.getY());
        int yDif = Math.abs(x - finishNode.getX());
        return Math.sqrt((xDif * xDif) + (yDif * yDif));

    }

    public void setWall() {
        isWall = true;
    }

    public boolean isWall() {
        return isWall;
    }

    public Double getFCost() {
        return gCost + hCost;
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }


    public void setPreviousNode(Node previousNode) {
        this.previousNode = previousNode;
    }

    public Node getPreviousNode() {
        return previousNode;
    }

    public void setGCost(double gCost) {
        this.gCost = gCost;
    }

    public void setHCost(double hCost) {
        this.hCost = hCost;
    }


    public double getGCost() {
        return gCost;
    }

    public Location getLocation() {
        return location;
    }

    @Override
    public String toString() {
        if (isWall) {
            return "W";
        }
        return ".";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Node)) {
            return false;
        }
        Node node = (Node) obj;
        return (node.getY() == y) && (node.getX() == x);
    }

    @Override
    public int compareTo(Node node) {
        return getFCost().compareTo(node.getFCost());
    }
}

