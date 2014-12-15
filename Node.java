package robots;

public class Node implements Comparable<Node>{
        public int x,y;
        public double g = Double.POSITIVE_INFINITY;
        public double h;
        public double f_x;

        public Node(int x_coord, int y_coord) {
            x = x_coord;
            y = y_coord;
        }

        public double getCost() {
            f_x = g+h;
            return f_x;
        }

        public int compareTo(Node other) {
            return Double.compare(f_x,other.f_x);
        }
}
