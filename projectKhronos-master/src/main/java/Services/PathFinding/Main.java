package Services.PathFinding;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        int width = 10;
        int height = 10;

        List<List<Node>> nodes = new ArrayList<>(); // to access a node: nodes.get(y).get(x)
        List<int[]> wallCoordinates = new ArrayList<int[]>() {
            {
                add(new int[]{0, 1});
                add(new int[]{1, 1});
                add(new int[]{2, 1});
                add(new int[]{3, 1});
                add(new int[]{3, 2});
            }
        };

        // setup map and set correct walls/ boundaries
        for (int y = 0; y < width; y++) {
            List<Node> row = new ArrayList<>();

            for (int x = 0; x < height; x++) {
                Node node = new Node(y, x);

                for (int[] cord : wallCoordinates) {
                    if (cord[0] == y && cord[1] == x) {
                        node.setWall();
                    }
                }
                row.add(node);
            }
            nodes.add(row);
        }


        PathFinder pf = new PathFinder(nodes); // create path finder object with map

        // Test 1: start(0, 0), end(2, 0)
        Node start = nodes.get(0).get(0); // get any start node
        Node end = nodes.get(0).get(2); // get any end node
        List<Node> path = pf.resolvePath(start, end); // resolve path from start node to end node
        pf.printMap(start, end, path); // print map with path

        // Test 1: start(0, 0), end(7, 7)
        start = nodes.get(0).get(0);
        end = nodes.get(7).get(7);
        pf.printMap(start, end, pf.resolvePath(start, end));

    }
}
