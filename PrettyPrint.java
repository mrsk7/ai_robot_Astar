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
            System.out.println("["+ tmp.x +","+ tmp.y +"]");
        }
    }

    public static void printMap(int[][] map,int N,int M) {
    int i,j;
        for (i=0;i<N;i++){
            for (j=0;j<M;j++)
                if (map[i][j] == -1) System.out.print('R');
                else if (map[i][j] == 1) System.out.print('X');
                else if (map[i][j] == 2) System.out.print('*');
                else System.out.print('O');
            System.out.println();
        }
    }
}
