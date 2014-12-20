package robots;

import java.io.*;
import java.lang.*;
import java.util.*;
import java.util.PriorityQueue;

public class Robots {
           
        public static Node AStar(Node start,Node Goal,int[][] obs,int N,int M) {
            start.h = Util.heuristic(start,Goal,'M');
            start.updateCost();
            PriorityQueue<Node> q1 = new PriorityQueue<Node>();
            q1.add(start);
            Node current1,tmp;
            LinkedList<Node> neighbours;
            Hashtable<Node,Node> closed_set = new Hashtable<Node,Node>();
            while (!q1.isEmpty()) {
                current1 = q1.poll();
                if (current1.equals(Goal)) return current1;
                closed_set.put(current1,current1);
                neighbours=Util.getNextNodes(current1,obs,N,M,Goal);
                System.out.println("Current Node " + "[" + current1.cds.x + "," + current1.cds.y + "]");
                for (Iterator<Node> iter = neighbours.iterator(); iter.hasNext();){
                    tmp = iter.next();
                    System.out.print("\tChild: " + "[" + tmp.cds.x + "," + tmp.cds.y + "]");
                    if (tmp.isBlocked(obs)) {System.out.println("\t **Blocked"); continue;}
                    if (closed_set.containsKey(tmp) || (q1.contains(tmp))) {System.out.println("\t\tAlready found"); continue;}
                    System.out.println();
                    tmp.h = Util.heuristic(tmp,Goal,'M');
                    tmp.updateCost();
                    q1.add(tmp);
                } 
            }
            return null; //For compile only. Program should never return from here
        }
    public static void run(Node Robot1,Node Robot2,Node Goal,int[][] obs,int N,int M) {
        Node ret = AStar(Robot1,Goal,obs,N,M);
        HashMap<Integer,Util.Coords> hm1 = Util.getPathByStep(ret);
        HashMap<Integer,Util.Coords> hm2;
        Util.Coords collision;
        ret = AStar(Robot2,Goal,obs,N,M);
        System.out.println("Simple print path");
        PrettyPrint.printPath(ret);
        System.out.println("End-Simple print path");
        hm2 = Util.getPathByStep(ret);
        while (Util.isCollision(hm1,hm2,Goal)) {
            System.out.println("Collision Detected");
            collision = Util.returnCollisionCoords(hm1,hm2);
            Util.updateObstacles(collision,obs);
            ret = AStar(Robot2,Goal,obs,N,M);
            hm2 = Util.getPathByStep(ret);
        }
        //System.out.println("Robot1 will go:");
        //PrettyPrint.printFromHash(hm1);        
        System.out.println("Robot2 will go:");
        PrettyPrint.printFromHash(hm2);        
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
        run(Robot1,Robot2,Goal,obstacles,N,M);
        //PrettyPrint.preetyPrintPath(ret,obstacles,N,M);
        //PrettyPrint.printMap(obstacles,N,M);
        //PrettyPrint.printPath(ret);
    }
}
