import java.io.*;
import java.lang.*;
import java.util.*;
import java.util.PriorityQueue;
import java.math.*;

public class Robots {

        public static class Node{
            public int x,y;

            public Node(int x_coord, int y_coord) {
                x = x_coord;
                y = y_coord;
            }

        }
        
        public static void AStar(Node Robot1,Node Robot2,Node Goal,int[][] obs) {
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
        try {
            line = reader.readLine();
        }
        catch (IOException ioex) {
            System.out.println("IOEXception");
            System.exit(1);
        }
        parts = line.split(" ");
        Node Robot1 = new Node(Integer.parseInt(parts[0]),Integer.parseInt(parts[1]));
        try {
            line = reader.readLine();
        }
        catch (IOException ioex) {
            System.out.println("IOEXception");
            System.exit(1);
        }
        parts = line.split(" ");
        Node Robot2 = new Node(Integer.parseInt(parts[0]),Integer.parseInt(parts[1]));
        try {
            line = reader.readLine();
        }
        catch (IOException ioex) {
            System.out.println("IOEXception");
            System.exit(1);
        }
        parts = line.split(" ");
        Node Goal = new Node(Integer.parseInt(parts[0]),Integer.parseInt(parts[1]));
        char[] tmp;
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
        AStar(Robot1,Robot2,Goal,obstacles);
    }
}
