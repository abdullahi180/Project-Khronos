package Services.PathFinding;

import Entity.Location;

import java.util.*;


public class PathFinder {

    private List<List<Node>> originalNodes;
    private List<List<Node>> nodes;
    private PriorityQueue<Node> openSet = new PriorityQueue<>();
    private List<Node> closedSet = new ArrayList<>();

    public PathFinder(Map<Location, String> map, int[] mapSize) {
        nodes = new ArrayList<>();
        for (int y = 0; y < mapSize[1] - 1; y++) { // mapSize[1] - 1 -> size in y axis
            nodes.add(new ArrayList<>());
        }
        for (Location location : map.keySet()) {
            int x = Math.round(location.getX());
            int y = Math.round(location.getY());
            Node node = new Node(y, x, location);
            if (location.isBoundary()) {
                node.setWall();
            }
            nodes.get(x).add(x, node);
        }
        originalNodes = new ArrayList<>(nodes); // keep copy of original un-modified map


    }

    public PathFinder(List<List<Node>> nodes) {
        originalNodes = nodes; // keep reference of original un-modified map
        this.nodes = new ArrayList<>(nodes); // copy of map for modification
    }

    /**
     * Prints a visual representation of the the path found, mainly for debugging, can be removed in the final version.
     * Example output:
     * <p>
     * S W . . . . . . . .
     * P W . . . . . . . .
     * P W . . . . . . . .
     * P W W . . . . . . .
     * . P . . . . . . . .
     * . . P . . . . . . .
     * . . . P P P . . . .
     * . . . . . . P E . .
     * . . . . . . . . . .
     * . . . . . . . . . .
     * {0,0} -> {1,0} -> {2,0} -> {3,0} -> {4,1} -> {5,2} -> {6,3} -> {6,4} -> {6,5} -> {7,6} -> {7,7}
     * <p>
     * S = start
     * P = path
     * W = wall/boundary
     * E = end/destination
     *
     * @param start        node from which the search started.
     * @param end          node to reach.
     * @param resolvedPath list of nodes in order from start to end.
     */
    public void printMap(Node start, Node end, List<Node> resolvedPath) {
        StringBuilder sb = new StringBuilder();
        for (int i = nodes.size() - 1; i >= 0; i--) {
            //for(int i =0; i < nodes.size(); i++){
            for (Node node : nodes.get(i)) {
                if (node.equals(start)) {
                    sb.append("S");
                } else if (node.equals(end)) {
                    sb.append("E");
                } else if (resolvedPath.contains(node)) {
                    sb.append("P");
                } else {
                    sb.append(node);
                }
                sb.append(" ");

            }
            sb.append("\n");
        }
        System.out.print(sb);
        System.out.print("{" + start.getX() + "," + start.getY() + "}");
        for (Node node : resolvedPath) {
            System.out.print(" -> {" + node.getX() + "," + node.getY() + "}");
        }
        System.out.print(" -> {" + end.getX() + "," + end.getY() + "}");
        System.out.print("\n");
    }

    public void printMap(Location start, Location end, List<Location> resolvedPath) {
        System.err.println("Currently Unsupported");
    }


    /**
     * Uses the A* algorithm to determine the most optimal path from node start to end node
     *
     * @param start node from which the search started.
     * @param end   node to reach.
     * @return list of nodes in order from start to end.
     */
    public List<Node> resolvePath(Node start, Node end) {
        if (start.isWall()) {
            // cannot start from wall
            return null;
        }
        openSet.add(start); // add start element to the open set
        Node current;
        while (!openSet.isEmpty()) {
            current = openSet.remove();
            closedSet.add(current);
            if (current.equals(end)) {
                reset();
                return backTrack(start, end);
            }
            for (Node neighbour : exploreNeighbours(current)) {
                if (!neighbour.isWall() && !closedSet.contains(neighbour)) {
                    // neighbour is not a wall and is not in the closed set
                    double newCostToNeighbour = current.getGCost() + current.calculateEuclideanDistance(neighbour);

                    if (newCostToNeighbour < neighbour.getGCost() || !openSet.contains(neighbour)) {
                        neighbour.setGCost(newCostToNeighbour);
                        neighbour.setHCost(neighbour.calculateEuclideanDistance(end));
                        neighbour.setPreviousNode(current);
                        if (!openSet.contains(neighbour)) {
                            openSet.add(neighbour);
                        }
                    }
                }
            }
        }
        reset();
        return new ArrayList<>(); // no path found return empty list
    }

    public List<Location> resolvePath(Location start, Location end) {
        Node startNode = nodes.get(Math.round(start.getY())).get(Math.round(start.getX()));
        Node endNode = nodes.get(Math.round(end.getY())).get(Math.round(end.getX()));
        List<Node> path = resolvePath(startNode, endNode);
        List<Location> pathAsLocations = new ArrayList<>();
        for (Node node : path) {
            pathAsLocations.add(node.getLocation());
        }
        return pathAsLocations;
    }

    /**
     * Finds the neighbours which surround
     *
     * @param node to explore.
     * @return list of nodes around the target node.
     */
    private List<Node> exploreNeighbours(Node node) {
        List<Node> neighbours = new ArrayList<>();
        for (int y = -1; y <= 1; y++) {
            for (int x = -1; x <= 1; x++) {
                if (y == 0 && x == 0)
                    continue;
                try {
                    neighbours.add(nodes.get(node.getY() + y).get(node.getX() + x));
                } catch (IndexOutOfBoundsException ignored) {
                }
            }
        }

        return neighbours;
    }

    /**
     * Backtracks from the end node through to the start node in order to find the path
     * that was taken.
     *
     * @param start node from which the search started.
     * @param end   node to reach.
     * @return list of nodes in order from start to end.
     */
    private List<Node> backTrack(Node start, Node end) {
        List<Node> finalPath = new ArrayList<>();
        Node currentNode = end;
        while (currentNode != start) {
            finalPath.add(currentNode);
            currentNode = currentNode.getPreviousNode();
        }
        finalPath.remove(0); // remove end node
        Collections.reverse(finalPath); // reverse path so it in correct order
        return finalPath;
    }

    /**
     * Rest temporary variables used in path search ready for new search.
     */
    private void reset() {
        nodes = new ArrayList<>(originalNodes);
        openSet.clear();
        closedSet.clear();

    }

}


