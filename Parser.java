package robots;

import java.io.*;
import java.lang.*;
import java.util.*;
public class Parser {

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
            Node ret = new Node(new Util.Coords(Integer.parseInt(parts[1])-1,Integer.parseInt(parts[0])-1));
            return ret;
        }
        

        public static int[][] parseObstacles(BufferedReader reader,int N,int M,Node a,Node b,Node g) {
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
            obstacles[a.cds.x][a.cds.y] = -1;
            obstacles[b.cds.x][b.cds.y] = -1;
            obstacles[g.cds.x][g.cds.y] = 2;
            return obstacles;
        }
}
