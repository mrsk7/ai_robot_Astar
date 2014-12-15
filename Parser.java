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
            Node ret = new Node(new Util.Coords(Integer.parseInt(parts[0]),Integer.parseInt(parts[1])));
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
            obstacles[a.cds.x-1][a.cds.y-1] = -1;
            obstacles[b.cds.x-1][b.cds.y-1] = -1;
            obstacles[g.cds.x-1][g.cds.y-1] = 2;
            return obstacles;
        }
}
