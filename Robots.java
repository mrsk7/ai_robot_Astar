import java.io.*;
import java.lang.*;
import java.util.*;
import java.util.PriorityQueue;
import java.math.*;

public class Robots {
        
        public static class Node{
            public int x,y;
            public double g,h;
            public double f_x;

            public Node(int x_coord, int y_coord) {
                x = x_coord;
                y = y_coord;
            }

            public renew_cost() {
                f_x = g+h;
            }

        }
        
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
            else h=0;
        }

        public static Node parseNode(BufferedReader reader) {
            String line = null;
            try {
                line = reader.readLine();
            }
            catch (IOException ioex) {
                System.out.println("IOEXception");
                System.exit(1);
            }
            String[] parts = line.split(" ");
            Node ret = new Node(Integer.parseInt(parts[0]),Integer.parseInt(parts[1]));
            return ret;
        }
        

        public static int[][] parseObstacles(BufferedReader reader,int N,int M) {
            char[] tmp;
            int[][] obstacles= new int[N][M];
            int i,j;
            String line = null;
            for (i=0;i<N;i++) {
                try {
                    line = reader.readLine();
                }
                catch (IOException ioex) {
                    System.out.println("IOEXception");
                    System.exit(1);
                }
                tmp = line.toCharArray();
                for (j=0;j<line.length();j++) 
                    if (tmp[j]=='X')
                        obstacles[i][j]=1;
            }
            return obstacles;
        }
        public static void run(Node Robot1,Node Robot2,Node Goal,int[][] obs) {
            Robot1.g = Robot2.g = 0;
            Robot1.h = heuristic(Robot1,Goal,'M');
            Robot2.h = heuristic(Robot2,Goal,'M');
            Robot1.renew_cost();
            Robot2.renew_cost();
            PriorityQueue<Node> q = new PriorityQueue<Node>();
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
        run(Robot1,Robot2,Goal,obstacles);
    }
}
