package robots;

import java.util.Stack;

public class PrettyPrint {
    public static void printPath(Node bottom) {
        Node curr = bottom;
        Util.Coords tmp;
        Stack<Util.Coords> stack = new Stack<Util.Coords>();       
        while (curr!=null) {
            stack.push(curr.cds);
            curr = curr.parent;
        }
        while (!stack.empty()) {
            tmp = stack.pop();
            System.out.println("["+ (tmp.x+1) +","+ (tmp.y+1) +"]");
        }
    }

    public static void printMap(int[][] map,int N,int M) {
    int i,j;
        for (i=0;i<N;i++){
            for (j=0;j<M;j++)
                if (map[i][j] == -1) System.out.print('R');     //For init node
                else if (map[i][j] == 1) System.out.print('X'); //For obstacle
                else if (map[i][j] == 2) System.out.print('*'); //For Goal
                else if (map[i][j] == 3) System.out.print('$'); //For route
                else System.out.print('O');
            System.out.println();
        }
    }

    public static void preetyPrintPath(Node bottom,int[][] map, int N, int M) {
        int[][] output_array = new int[N][M];
        int i,j;
        for (i=0;i<N;i++){
            for (j=0;j<M;j++)
                output_array[i][j]= map[i][j];
        }
        Node curr = bottom;
        Util.Coords tmp;
        Stack<Util.Coords> stack = new Stack<Util.Coords>();
        while (curr!=null) {
            stack.push(curr.cds);
            curr = curr.parent;
        }
        while (!stack.empty()) {
            tmp = stack.pop();
            output_array[tmp.x][tmp.y] = 3;
        }
        printMap(output_array,N,M);
    }
}
