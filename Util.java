package robots;

import java.util.*;
import java.math.*;

public class Util {

        public static class Coords {
            int x,y;
            
            public Coords(int a,int b) {
                x=a;
                y=b;
            }
            //This method overrides Object.equals()
            public boolean equals(Object obj) {
                    if(obj instanceof Coords) {
                            Coords toCompare = (Coords) obj;
                            return ((x == toCompare.x) && (y == toCompare.y));
                    }
                    return false;
            }
            //This method overrides Object.hashCode()
            public int hashCode() {
                int hash = 1;
                hash = hash * 31 + x;
                hash = hash * 31 + y;
                return hash;
            }
        }

        public static double heuristic(Node a,Node goal,char choice) {
            double h = 0;
            if (choice == 'M') {
                //This is Manhattan heuristic (admissible)
                h = Math.abs(a.cds.x-goal.cds.x) + Math.abs(a.cds.y-goal.cds.y);
            }
            else if (choice == 'E') {
                //Eucleidian heuristic (admissible)
                h = Math.sqrt(Math.pow(a.cds.x-goal.cds.x,2) + Math.pow(a.cds.y-goal.cds.y,2));
            }
            else if (choice == 'D') {
                h=0;       //Djikstra
            }
            else if (choice == 'S') {
                //This is snake heuristic (non-admissible). It's named after the Nokia Snake game
                h = Math.abs(a.cds.x-goal.cds.x) * Math.abs(a.cds.y-goal.cds.y);
            }
            else throw new RuntimeException();
            return h;
        }

        public static LinkedList<Node> getNextNodes(Node curr,int[][] obs,int N,int M,Node Goal) {
            //Four possible moves for every position, up, down, right or left
            LinkedList<Node> ll =  new LinkedList<Node>();
            //if not at the top of the map
            if (!(curr.cds.x == 0))  {
                Node up = new Node(new Coords(curr.cds.x-1,curr.cds.y),curr.g+1,curr);
                ll.add(up);
            }
            //if not at the bottom of the map
            if (!(curr.cds.x == N-1)) {
                Node down = new Node(new Coords(curr.cds.x+1,curr.cds.y),curr.g+1,curr);
                ll.add(down);
            }
            //if not at the right edge of the map
            if (!(curr.cds.y == M-1)) {
                Node right = new Node(new Coords(curr.cds.x ,curr.cds.y+1),curr.g+1,curr);
                ll.add(right);
            }
            //if not at the left edge of the map
            if (!(curr.cds.y == 0)) {
                Node left = new Node(new Coords(curr.cds.x,curr.cds.y-1),curr.g+1,curr);
                ll.add(left);
            }
            return ll;
        }

        public static HashMap<Integer,Coords> getPathByStep(Node bottom) {
            HashMap<Integer,Coords> hm = new HashMap<Integer,Coords>();
            Node curr = bottom;
            Util.Coords tmp;
            Stack<Coords> stack = new Stack<Coords>();       
            while (curr!=null) {
                stack.push(curr.cds);
                curr = curr.parent;
            }
            int i=0;
            while (!stack.empty()) {
                tmp = stack.pop();
                hm.put(i,tmp);
                i++;
            }
            return hm;
        }

        public static boolean isCollision(HashMap<Integer,Coords> h1,HashMap<Integer,Coords> h2,Node Goal) {
            int bound = Math.min(h1.size(),h2.size());
            int i=1;
            while (i<bound) {
                if (h1.get(i).equals(h2.get(i))) return true;
                i++;
            }
            return false;
        }

        public static Coords returnCollisionCoords(HashMap<Integer,Coords> h1,HashMap<Integer,Coords> h2) {
            int bound = Math.min(h1.size(),h2.size());
            int i=1;
            while (i<bound) {
                if (h1.get(i).equals(h2.get(i))) {
                    System.out.println("Collision found at ["+(h1.get(i).x+1) +","+ (h1.get(i).y+1) +"]");
                    return h1.get(i);
                }
                i++;
            }
            return null;    //Never to get here. Only for error-free compilation
        }

        public static void updateObstacles(Coords cds,int[][] obs) {
            obs[cds.x][cds.y] = 1;
        }
}
