package robots;

public class Node implements Comparable<Node>{
        public Util.Coords cds;
        public double g;
        public double h;
        public double f_x;
        
        public Node parent;

        public Node(Util.Coords c) {
            cds = c;
            g = 0;
            parent = null;
        }

        public Node(Util.Coords c,double init_cost,Node from) {
            this.cds = c;
            g = init_cost;
            parent = from;
        }

        public void updateCost() {
            f_x = g+h;
        }

        public int compareTo(Node other) {
            return Double.compare(f_x,other.f_x);
        }
        
        //This method overrides Object.equals()
        public boolean equals(Object obj) {
                if(obj instanceof Node) {
                        Node toCompare = (Node) obj;
                        return (cds.equals(toCompare.cds)&&(cds.equals(toCompare.cds)));
                }
                return false;
        }

        //This method overrides Object.hashCode()
        public int hashCode() {
            int hash = 1;
            hash = hash * 31 + this.cds.hashCode();
            hash = hash * 31 + this.cds.hashCode();
            return hash;
        }
}
