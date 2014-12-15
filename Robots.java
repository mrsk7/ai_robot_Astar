package robots;

import java.io.*;
import java.lang.*;
import java.util.*;
import java.util.PriorityQueue;
import java.math.*;

public class Robots {
           
        
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
            else throw new RuntimeException();
            return h;
        }

        public static LinkedList<Node> getNextNodes(Node curr,int[][] obs,int N,int M,Node Goal) {
            //Four possible moves for every position, up, down, right or left
            LinkedList<Node> ll =  new LinkedList<Node>();
            //if not at the top of the map
            if (!(curr.cds.y == 0))  {
                Node up = new Node(new Util.Coords(curr.cds.x,curr.cds.y-1),curr.g+1,curr);
                ll.add(up);
            }
            //if not at the bottom of the map
            if (!(curr.cds.y == M-1)) {
                Node down = new Node(new Util.Coords(curr.cds.x,curr.cds.y+1),curr.g+1,curr);
                ll.add(down);
            }
            //if not at the right edge of the map
            if (!(curr.cds.x == N-1)) {
                Node right = new Node(new Util.Coords(curr.cds.x+1 ,curr.cds.y),curr.g+1,curr);
                ll.add(right);
            }
            //if not at the left edge of the map
            if (!(curr.cds.y == 0)) {
                Node left = new Node(new Util.Coords(curr.cds.x-1,curr.cds.y),curr.g+1,curr);
                ll.add(left);
            }
            return ll;
        }

        public static Node AStar(Node Robot1,Node Robot2,Node Goal,int[][] obs,int N,int M) {
            Robot1.h = heuristic(Robot1,Goal,'M');
            Robot1.updateCost();
            PriorityQueue<Node> q1 = new PriorityQueue<Node>();
            q1.add(Robot1);
            Node current1,tmp;
            LinkedList<Node> neighbours;
            Hashtable<Node,Node> closed_set = new Hashtable<Node,Node>();
            while (!q1.isEmpty()) {
                current1 = q1.poll();
                if (current1.equals(Goal)) return current1;
                closed_set.put(current1,current1);
                neighbours=getNextNodes(current1,obs,N,M,Goal);
                for (Iterator<Node> iter = neighbours.iterator(); iter.hasNext();){
                    tmp = iter.next();
                    if (closed_set.containsKey(tmp) || (q1.contains(tmp))) continue;
                    tmp.h = heuristic(Robot1,Goal,'M');
                    tmp.updateCost();
                    q1.add(tmp);
                } 
            }
            return null; //For compile only. Program should never return from here
        }

    public static void main(String[] args) {
        if (args.length<=0) {
            System.out.println("No input file given");
            System.exit(1);
        }
        int N,M,i,j;
        FileReader fileInput = null;
        try {
            fileInput = new FileReader("./"+args[0]);
        }
        catch(FileNotFoundException ex) {
            System.out.println("File not found");
            System.exit(1);
        }
        BufferedReader reader = new BufferedReader(fileInput);
        String line = null;
        try {
            line = reader.readLine();
        }
        catch (IOException ioex) {
            System.out.println("IOEXception");
            System.exit(1);
        }
        String[] parts = line.split(" ");
        M = Integer.parseInt(parts[0]);
        N = Integer.parseInt(parts[1]);
        int[][] obstacles= new int[N][M];
        Node Robot1 = Parser.parseNode(reader);
        Node Robot2 = Parser.parseNode(reader);
        Node Goal = Parser.parseNode(reader);
        obstacles = Parser.parseObstacles(reader,N,M,Robot1,Robot2,Goal);
        Node ret = AStar(Robot1,Robot2,Goal,obstacles,N,M);
        PrettyPrint.printMap(obstacles,N,M);
        PrettyPrint.printPath(ret);
    }
}
