          /* ***************************************************************************/
/* Η υλοποίηση του προβλήματος Bats χρησιμοποιεί τα εξής:						*
 * Σε κάθε νυχτερίδα αντιστοιχεί μια δομή BatStruct (προφανώς)					*
 * η οποία περιλαμβάνει τις συντεταγμένες της x,y, το index						*
 * μεσα στον πίνακα batsArray, μια συνδεδεμένη λίστα στην οποία					*
 * αποθηκεύονται οι γείτονές της (ll) και τη μεταβλητή distance					*
 * η οποία χρησιμοποιείται απο τον αλγόριθμο του Djikstra.						*
 * Όλες οι νυχτερίδες αποθηκεύονται στον πίνακα batsArray και 					*
 * όλοι οι τοίχοι στον τετραγωνικό πίνακα walls. Ενας τοίχος 					*
 * υπάρχει στη θέση (x,y) εαν και μόνο εαν το walls[x][y] = 1.					*
 * Η πορεία προς επίλυση είναι η ακόλουθη:											*
 * Αφού διαβαστεί η είσοδος κατάλληλα και συμπληρωθούν οι πίνακες 				*
 * batsArray και walls, ελέγχεται για κάθε πιθανό ζευγάρι νυχτερίδων 			*
 * εαν επικονωνούν απευθείας ή ένας τοίχος βρίσκεται ενδιάμεσα τους.			*
 * Αυτό γίνεται εκτελώντας τον αλγόριθμο του Bresenham και ελέγχοντας			*
 * αν στην ευθεία που ορίζουν οι δυο νυχτερίδες βρίσκεται ενδιάμεσα τοίχος.		*
 * Κάθε φορά που η επικονωνία μεταξύ ενός ζευγους νυχτερίδων είναι ανεμπόδιστη	*
 * προστίθεται η μια νυχτερίδα στη συνδεδεμένη λίστα ll της άλλης.				*
 * Έτσι κατασκευάζεται ένας γράφος απο νυχτερίδες και την αράχνη.				*
 * Εκτελώντας τον αλγόριθμο του Dijkstra απο τη αρχή του γράφου το πρόβλημα		*
 * ερχεται εις πέρας															*/
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
        //Συνάρτηση υλοποίηση στρογγυλοποίησης με places αριθμό δεκαδικών ψηφίων
        public static double round(double value, int places) {
            if (places < 0) throw new IllegalArgumentException();

            BigDecimal bd = new BigDecimal(value);
            bd = bd.setScale(places, RoundingMode.HALF_UP);
            return bd.doubleValue();
        }
        // http://lifc.univ-fcomte.fr/home/~ededu/projects/bresenham/
        // Ο αλγόριθμος του Bresenham δέχεται δυο σημεία (x1,y1) (x2,y2) και 
        // κατασκευάζει την ευθεία που διέρχεται ανάμεσά τους. Στη συγκεκριμένη υλοποίηση
        // δέχεται ένα επιπλέον όρισμα, τον τετραγωνικό πίνακα που περιλαμβάνει τους τοίχους 
        // και δεν επιστρέφει την ευθεία μεταξύ των σημείων αλλά ελέγχει για κάθε παραγόμενο
        // σημείο της ευθείας αν το σημείο αυτό αντιστοιχεί σε τοίχο. Αν ναι τότε επιστρέφει
        // false αλλίως αν όλα τα σημεία της ευθείας δεν αντιστοιχούν σε τοίχο τότε επιστρέφει true
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

        // Υλοποίηση του αλγορίθμου του Dijkstra. Χρησιμοποιεί μια Priority Queue 
        // απο BatStruct. Οι γείτονες κάθε κόμβου βρίσκονται στη LinkedList ll της
        // δομής BatStruct.
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
            if (parts[2].equals("B")) {	//Κάθε νυχτερίδα προστίθεται στον πίνακα batsArray ως μια δομή BatStruct
                batsArray[bat_cnt] = new BatStruct(Integer.parseInt(parts[0]),Integer.parseInt(parts[1]),bat_cnt);
                bat_cnt++;
            }
            else if (parts[2].equals("-"))  {		//Κάθε φορά που βρίσκει τοίχο θέτει 1 στο αντίστοιχο σημείο του walls
                walls[Integer.parseInt(parts[0])][Integer.parseInt(parts[1])] = 1;
            }
            else {
                spider_idx = bat_cnt;	//Η αράχνη θεωρείται ως μια απλή νυχτερίδα, της οποίας όμως κρατάω το index για να βρω
                                        //υπολογισμένη απόσταση απο τον Dijkstra
                batsArray[bat_cnt] = new BatStruct(Integer.parseInt(parts[0]),Integer.parseInt(parts[1]),bat_cnt); 
                bat_cnt++;
            }
        }
        for (i=0;i<bat_cnt;i++) {
            for (j=i+1;j<bat_cnt;j++) {
                boolean noWallBetween = Bresenham(batsArray[i].x,batsArray[i].y,batsArray[j].x,batsArray[j].y,walls); 
                //Για κάθε ζεύγος νυχτερίδων, αν δεν υπάρχει τοίχος ενδιάμεσα τότε πρόστίθεται η μια ως γείτονας της άλλης
                //στη LinkedList ll.
                if (noWallBetween) {
                    batsArray[i].ll.add(batsArray[j]);
                    batsArray[j].ll.add(batsArray[i]);
                }
            }
        }
        //H computePath εκτελεί τον αλγόριθμο του Dijkstra και επιστρέφει την απόσταση που έχει
        //υπολογιστεί για τον κόμβο με index spider_index, δηλαδή για την αράχνη.
        //Στη συνέχεια εκτελείται στρογγυλοποίηση 2 δεκαδικών ψηφίων
        System.out.println(round(computePath(0,batsArray,spider_idx),2));
    }
}