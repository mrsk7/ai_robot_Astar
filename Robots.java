          /* ***************************************************************************/
/* � ��������� ��� ����������� Bats ������������ �� ����:						*
 * �� ���� ��������� ����������� ��� ���� BatStruct (��������)					*
 * � ����� ������������ ��� ������������� ��� x,y, �� index						*
 * ���� ���� ������ batsArray, ��� ����������� ����� ���� �����					*
 * ������������� �� �������� ��� (ll) ��� �� ��������� distance					*
 * � ����� ��������������� ��� ��� ��������� ��� Djikstra.						*
 * ���� �� ���������� ������������� ���� ������ batsArray ��� 					*
 * ���� �� ������ ���� ����������� ������ walls. ���� ������ 					*
 * ������� ��� ���� (x,y) ��� ��� ���� ��� �� walls[x][y] = 1.					*
 * � ������ ���� ������� ����� � ��������:											*
 * ���� ��������� � ������� ��������� ��� ������������ �� ������� 				*
 * batsArray ��� walls, ��������� ��� ���� ������ ������� ���������� 			*
 * ��� ����������� ��������� � ���� ������ ��������� ��������� ����.			*
 * ���� ������� ���������� ��� ��������� ��� Bresenham ��� ����������			*
 * �� ���� ������ ��� ������� �� ��� ���������� ��������� ��������� ������.		*
 * ���� ���� ��� � ���������� ������ ���� ������� ���������� ����� �����������	*
 * ����������� � ��� ��������� ��� ����������� ����� ll ��� �����.				*
 * ���� �������������� ���� ������ ��� ���������� ��� ��� ������.				*
 * ���������� ��� ��������� ��� Dijkstra ��� �� ���� ��� ������ �� ��������		*
 * ������� ��� �����															*/
import java.io.*;
import java.lang.*;
import java.util.*;
import java.util.PriorityQueue;
import java.math.*;

public class Bats {

        public static class BatStruct implements Comparable<BatStruct>{
            public int x,y;
            public int index;
            public LinkedList<BatStruct> ll; 
            public double distance = Double.POSITIVE_INFINITY;

            public BatStruct(int x_coord, int y_coord,int idx) {
                x = x_coord;
                y = y_coord;
                index = idx;
                ll = new LinkedList<BatStruct>();
            }
            public int compareTo(BatStruct other)
            {
                return Double.compare(distance, other.distance);
            }

        }
        //��������� ��������� ���������������� �� places ������ ��������� ������
        public static double round(double value, int places) {
            if (places < 0) throw new IllegalArgumentException();

            BigDecimal bd = new BigDecimal(value);
            bd = bd.setScale(places, RoundingMode.HALF_UP);
            return bd.doubleValue();
        }
        // http://lifc.univ-fcomte.fr/home/~ededu/projects/bresenham/
        // � ���������� ��� Bresenham ������� ��� ������ (x1,y1) (x2,y2) ��� 
        // ������������ ��� ������ ��� ��������� ������� ����. ��� ������������ ���������
        // ������� ��� �������� ������, ��� ����������� ������ ��� ������������ ���� ������� 
        // ��� ��� ���������� ��� ������ ������ ��� ������� ���� ������� ��� ���� ����������
        // ������ ��� ������� �� �� ������ ���� ����������� �� �����. �� ��� ���� ����������
        // false ������ �� ��� �� ������ ��� ������� ��� ������������ �� ����� ���� ���������� true
        public static boolean Bresenham(int x1,int y1,int x2, int y2,int[][] walls) {
            int xstep,ystep;
            int x=x1;
            int y=y1;
            int dx = x2-x1;
            int dy = y2 - y1;
            int i = 0;
            if (dy<0) {
                ystep = -1;
                dy = -dy;
            }
            else ystep = 1;
            if (dx<0) {
                xstep = -1;
                dx = -dx;
            }
            else xstep = 1;
            int ddx = 2*dx;
            int ddy = 2*dy;
            int errorprev,error;
            if (ddx>=ddy) {
                errorprev = error = dx;
                while (i<dx) {
                    x+=xstep;
                    error+=ddy;
                    if (error > ddx) {
                        y += ystep;
                        error -= ddx;
                        if (error + errorprev < ddx) {
                            if (walls[x][y-ystep]==1) {
                                    return false;
                            }
                        }
                        else if (error + errorprev > ddx) {
                            if (walls[x-xstep][y]==1) {
                                    return false;
                            }
                        }
                        else {
                            if (walls[x][y-ystep]==1) {
                                    return false;
                            }
                            if (walls[x-xstep][y]==1) {
                                    return false;
                            }
                        }
                    }
                    if (walls[x][y]==1) {
                            return false;
                    }
                    errorprev = error;
                    i += 1;
                }
            }
            else {
                errorprev = error = dy;
                while (i < dy) {
                    y += ystep;
                    error += ddx;
                    if (error > ddy) {
                        x += xstep;
                        error -= ddy;
                        if (error + errorprev < ddy) {
                            if (walls[x-xstep][y]==1) {
                                    return false;
                            }
                        }
                        else if (error + errorprev > ddy) {
                            if (walls[x][y-ystep]==1) {
                                    return false;
                            }
                        }
                        else {
                            if (walls[x-xstep][y]==1) {
                                    return false;
                            }
                            if (walls[x][y-ystep]==1) {
                                    return false;
                            }
                        }
                    }
                    if (walls[x][y]==1) {
                            return false;
                    }
                    errorprev = error;
                    i += 1;
                }
            }
            return true;
        }

        // ��������� ��� ���������� ��� Dijkstra. ������������ ��� Priority Queue 
        // ��� BatStruct. �� �������� ���� ������ ���������� ��� LinkedList ll ���
        // ����� BatStruct.
        public static double computePath(int start,BatStruct[] batArray,int spider_idx) {
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




    public static void main(String[] args) {
        if (args.length<=0) {
            System.out.println("No input file given");
            System.exit(1);
        }
        int N,M,K,i,j,k;
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
        N = Integer.parseInt(parts[0]);
        M = Integer.parseInt(parts[1]);
        K = Integer.parseInt(parts[2]);
        int bat_cnt,wall_cnt;
        int spider_idx = 0;
        bat_cnt=0;
        int[][] walls = new int[N][M];
        BatStruct[] batsArray = new BatStruct[K];
        for (i=0;i<K;i++) {
            try {
                line = reader.readLine();
            }
            catch (IOException ioex) {
                System.out.println("IOEXception");
                System.exit(1);
            }
            parts = line.split(" ");
            if (parts[2].equals("B")) {	//���� ��������� ����������� ���� ������ batsArray �� ��� ���� BatStruct
                batsArray[bat_cnt] = new BatStruct(Integer.parseInt(parts[0]),Integer.parseInt(parts[1]),bat_cnt);
                bat_cnt++;
            }
            else if (parts[2].equals("-"))  {		//���� ���� ��� ������� ����� ����� 1 ��� ���������� ������ ��� walls
                walls[Integer.parseInt(parts[0])][Integer.parseInt(parts[1])] = 1;
            }
            else {
                spider_idx = bat_cnt;	//� ������ ��������� �� ��� ���� ���������, ��� ������ ���� ������ �� index ��� �� ���
                                        //������������ �������� ��� ��� Dijkstra
                batsArray[bat_cnt] = new BatStruct(Integer.parseInt(parts[0]),Integer.parseInt(parts[1]),bat_cnt); 
                bat_cnt++;
            }
        }
        for (i=0;i<bat_cnt;i++) {
            for (j=i+1;j<bat_cnt;j++) {
                boolean noWallBetween = Bresenham(batsArray[i].x,batsArray[i].y,batsArray[j].x,batsArray[j].y,walls); 
                //��� ���� ������ ����������, �� ��� ������� ������ ��������� ���� ����������� � ��� �� �������� ��� �����
                //��� LinkedList ll.
                if (noWallBetween) {
                    batsArray[i].ll.add(batsArray[j]);
                    batsArray[j].ll.add(batsArray[i]);
                }
            }
        }
        //H computePath ������� ��� ��������� ��� Dijkstra ��� ���������� ��� �������� ��� ����
        //����������� ��� ��� ����� �� index spider_index, ������ ��� ��� ������.
        //��� �������� ���������� ��������������� 2 ��������� ������
        System.out.println(round(computePath(0,batsArray,spider_idx),2));
    }
}