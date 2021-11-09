import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    static class Node {
        public int x, y;
        public ArrayList<Node> neighbours = new ArrayList<>();
        public double distanceSoFar = Double.MAX_VALUE;
        public Node(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    static class Edge {
        public Node a, b;

        public Edge(Node a, Node b) {
            this.a = a;
            this.b = b;
        }
    }

    static class Path {
        public Node s, t;

        public Path(Node s, Node t) {
            this.s = s;
            this.t = t;
        }
    }

    static ArrayList<Path> paths = new ArrayList<>();
    static ArrayList<Edge> edges = new ArrayList<>();
    static ArrayList<Node> nodes = new ArrayList<>();

    static ArrayList<Node> opened = new ArrayList<>();

    static int numOfPaths;
    static int numOfNodes;
    static int numOfEdges;

    public static void main(String[] args) {
        readData();
        buildNeighbours();
        for (Path p : paths) {
            System.out.printf("%.2f" + '\t', dijkstraAlgorithm(p.s,p.t));
        }
    }

    private static double dijkstraAlgorithm(Node source, Node target) {


        initNodes();

        source.distanceSoFar = 0;
        opened.add(source);

        while (!opened.isEmpty()) {

            Node current = opened.get(0);
            for (int i = 1; i<opened.size();i++) {
                if (opened.get(i).distanceSoFar < current.distanceSoFar) {
                    current = opened.get(i);
                }
            }

            opened.remove(current);

            if (current.x == target.x && current.y == target.y) {
                return current.distanceSoFar;
            }

            for (Node neighbour : current.neighbours) {
                if (!opened.contains(neighbour)) {
                    continue;
                }

                double distanceCost = current.distanceSoFar + distanceBetweenTwoNodes(current, neighbour);

                if (distanceCost < neighbour.distanceSoFar) {
                    neighbour.distanceSoFar = distanceCost;
                    opened.add(neighbour);
                }
            }
        }

        return -1;
    }

    private static void initNodes() {
        for (Node n: nodes) {
            opened.add(n);
            n.distanceSoFar = Double.MAX_VALUE;
        }
    }

    private static double distanceBetweenTwoNodes(Node source, Node target) {
        double a = target.x-source.x;
        double b = target.y-source.y;
        return Math.sqrt(a*a+b*b);
    }

    private static void buildNeighbours() {

        for (Edge e : edges) {
            e.a.neighbours.add(e.b);
            e.b.neighbours.add(e.a);
        }
    }

    private static void readData() {
        Scanner sc = new Scanner(System.in);
        numOfPaths = Integer.parseInt(sc.nextLine());
        numOfNodes = Integer.parseInt(sc.nextLine());
        numOfEdges = Integer.parseInt(sc.nextLine());
        ArrayList<Integer> pathsTmp = new ArrayList<>();
        sc.nextLine();

        String[] tokens;

        for (int i = 0; i < numOfPaths; i++) {
            String line = sc.nextLine();
            tokens = line.split("\\t");
            pathsTmp.add(Integer.parseInt(tokens[0]));
            pathsTmp.add(Integer.parseInt(tokens[1]));
        }

        sc.nextLine();  // for the line with a single \n

        for (int i = 0; i < numOfNodes; i++) {
            String line = sc.nextLine();
            tokens = line.split("\\t");
            nodes.add(new Node(Integer.parseInt(tokens[0]), Integer.parseInt(tokens[1])));
        }

        sc.nextLine();  // for the line with a single \n

        for (int i = 0; i < numOfEdges; i++) {
            String line = sc.nextLine();
            tokens = line.split("\\t");
            //edges.add(new Edge(nodes.get(Integer.parseInt(tokens[0])), nodes.get(Integer.parseInt(tokens[1]))));
            Node a = nodes.get(Integer.parseInt(tokens[0]));
            Node b = nodes.get(Integer.parseInt(tokens[1]));
            edges.add(new Edge(a, b));
        }

        for (int i = 0; i < pathsTmp.size(); i+=2) {
            paths.add(new Path(nodes.get(pathsTmp.get(i)),nodes.get(pathsTmp.get(i+1))));
        }

    }
}
