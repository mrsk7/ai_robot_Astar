package robots;

import java.lang.*;
import java.util.*;
import java.util.PriorityQueue;
import java.math.*;

public class Robots {
           
        
        public static int heuristic(Node a,Node goal,char choice) {
            double h;
            if (choice = 'M') {
                //This is Manhattan heuristic (admissible)
                h = abs(a.x-goal.x) + abs(a.y-goal.y);
            }
            else if (choice = 'E' {
                //Eucleidian heuristic (admissible)
                h = Math.sqrt(Math.pow(a.x-goal.x,2) + Math.pow(a.y-goal.y,2))
            }
            else if (choice = 'D') {
                h=0;       //Djikstra
            }
            else ;
        }

        public static LinkedList<Nodes> getNextNodes(Node curr,int[][] obs,N,M) {
            //Four possible moves for every position, up, down, right or left
            LinkdedList<Nodes> ll =  newLinkedList<Nodes>();
            //if not at the top of the map
            if (!(curr.y == 0))  {
                Node up = new Node(curr.x,curr.y - 1);
                ll.add(up);
            }
            //if not at the bottom of the map
            if (!(curr.y == M-1)) {
                Node down = new Node(curr.x,curr.y +1);
                ll.add(down);
            }
            //if not at the right edge of the map
            if (!(curr.x == N-1)) {
                Node right = new Node(curr.x +1 ,curr.y);
                ll.add(right);
            }
            //if not at the left edge of the map
            if (!(curr.y == 0)) {
                Node left = new Node(curr.x - 1,curr.y);
                ll.add(left);
            }
            return ll;
        }

        public static void run(Node Robot1,Node Robot2,Node Goal,int[][] obs,N,M) {
            Robot1.g = Robot2.g = 0;
            Robot1.h = heuristic(Robot1,Goal,'M');
            Robot2.h = heuristic(Robot2,Goal,'M');
            Robot1.renew_cost();
            Robot2.renew_cost();
            PriorityQueue<Node> q1 = new PriorityQueue<Node>();
            PriorityQueue<Node> q2 = new PriorityQueue<Node>();
            q1.add(Robot1);
            q2.add(Robot2);
            Node current1,current2;
            LinkdedList<Node> neighbours;
            iState curState = new iState(Robot1,Robot2);
            Hashtable<iState,iState> closed_set = new Hashtable<iState,iState>();
            while ((!q1.isEmpty()) && (!q2.isEmpty())) {
                current1 = q1.poll();
                current2 = q2.poll();
                neighbours=getNextNodes(current1,obs,N,M);
                
            }
        }

        /*public static double computePath(int start,BatStruct[] batArray,int spider_idx) {
            double newdistance;
            PriorityQueue<BatStruct> q = new PriorityQueue<BatStruct>();
            batArray[start].distance = 0;
            q.add(batArray[start]);
            BatStruct current,neighbour;
            while (!q.isEmpty()) {
                current = q.poll();
                for (Iterator<BatStruct> iter = current.ll.iterator(); iter.hasNext();){
                    neighbour = iter.next();
                    newdistance = current.distance+Math.sqrt(Math.pow(current.x-neighbour.x,2)+Math.pow(current.y-neighbour.y,2));
                    if (newdistance < neighbour.distance) {
                        q.remove(neighbour);
                        neighbour.distance = newdistance;
                        q.add(neighbour);
                    }
                }
            }
            return batArray[spider_idx].distance;
        }
*/



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
        Node Robot1 = parseNode(reader);
        Node Robot2 = parseNode(reader);
        Node Goal = parseNode(reader);
        obstacles = parseObstacles(reader,N,M);
        /*System.out.println("Robot1:x=" + Robot1.x + "y=" + Robot1.y);
        System.out.println("Robot2:x=" + Robot2.x + "y=" + Robot2.y);
        System.out.println("Goal:x=" + Goal.x + "y=" + Goal.y);
        for (i=0;i<N;i++){
            for (j=0;j<M;j++)
                System.out.print(obstacles[i][j]);
            System.out.println();
        }*/
        run(Robot1,Robot2,Goal,obstacles,N,M);
    }
}
