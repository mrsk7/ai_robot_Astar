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
}
