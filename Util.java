package robots;

public class Util {

        public static class Coords {
            int x,y;
            
            public Coords(int a,int b) {
                x=a;
                y=b;
            }
            //This method overrides Object.equals()
            public boolean equals(Object obj) {
                    if(obj instanceof Coords) {
                            Coords toCompare = (Coords) obj;
                            return ((x == toCompare.x) && (y == toCompare.y));
                    }
                    return false;
            }
            //This method overrides Object.hashCode()
            public int hashCode() {
                int hash = 1;
                hash = hash * 31 + x;
                hash = hash * 31 + y;
                return hash;
            }
        }

        public static class iState{
            public Coords robot1_coords,robot2_coords;

            public iState(Node a, Node b) {
                robot1_coords = new Coords(a.cds.x,a.cds.y);
                robot2_coords = new Coords(b.cds.x,b.cds.y);
            }

            //This method overrides Object.equals()
            public boolean equals(Object obj) {
                    if(obj instanceof iState) {
                            iState toCompare = (iState) obj;
                            return ((robot1_coords.equals(toCompare.robot1_coords))&&(robot2_coords.equals(toCompare.robot2_coords)));
                    }
                    return false;
            }

            //This method overrides Object.hashCode()
            public int hashCode() {
                int hash = 1;
                hash = hash * 31 + robot1_coords.hashCode();
                hash = hash * 31 + robot2_coords.hashCode();
                return hash;
            }
            
            //TODO 
            public boolean isGoal(String goal) {
                    if (true) return true;
                    else return false;
            }
        }

}
