import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Grid implements Serializable {
    private Box[][] grid;
    private List<Boat> aryBoat = new ArrayList<>();

    private final int W = 10;
    private final int H = 10;

    public Grid() {
        this.grid = new Box[W][H];
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                this.grid[i][j] = new Box(i, j);
            }
        }

        /*aryBoat.add(new Boat(2, -1, -1, 0));
        aryBoat.add(new Boat(3, -1, -1, 0));
        aryBoat.add(new Boat(4, -1, -1, 0));
        aryBoat.add(new Boat(5, -1, -1, 0));*/
    }

    public int getW() {
        return W;
    }

    public int getH() {
        return H;
    }

    public Box getBox(int x, int y) {
        return this.grid[x][y];
    }

    public List<Boat> getAryBoat() {
        return aryBoat;
    }

    public void setAryBoat(List<Boat> aryBoat) {
        this.aryBoat = aryBoat;
    }

    /*public boolean addBoat(int i, Boat b) {
        if (isPossible(b)) {
            this.aryBoat.get(i).setOrientation(b.getOrientation());
            this.aryBoat.get(i).setX(b.getX());
            this.aryBoat.get(i).setY(b.getY());
            int x = b.getX();
            int y = b.getY();
            int size = b.getLongueur();
            if (b.getOrientation() == 0) {
                for (int j = y; j < y + size; j++) {
                    this.grid[x][j].setState(1);
                }
            } else {
                for (int j = x; j < x + size; j++) {
                    this.grid[j][y].setState(1);
                }
            }
            return true;
        } else {
            return false;
        }
    }*/

    private boolean isPossible(Boat b) {
        int x = b.getX();
        int y = b.getY();
        int size = b.getLongueur();

        if (b.getOrientation() == 0) {
            for (int i = y; i < y + size; i++) {
                if (this.grid[x][i].getState() == 1) {
                    return false;
                }
            }
            return true;
        } else if (b.getOrientation() == 1) {
            for (int i = x; i < x + size; i++) {
                if (this.grid[i][y].getState() == 1) {
                    return false;
                }
            }
            return true;
        } else {
            return false;
        }
    }

    public List<int[]> updateCpt(Box b) {
        for (int i = 0; i < aryBoat.size(); i++) {
            Boat boat = aryBoat.get(i);
            if (boat.getOrientation() == 0) {
                for (int j = boat.getY(); j < (boat.getY() + boat.getLongueur()); j++) {
                    if (b.getY() == j && b.getX() == boat.getX()) {
                        this.aryBoat.get(i).setCpt();

                        if (this.aryBoat.get(i).getCpt() == this.aryBoat.get(i).getLongueur()) {
                            /*System.out.println(aryCoord(this.aryBoat.get(i)).size());
                            System.out.println(aryCoord(this.aryBoat.get(i)).get(0)[0]);
                            System.out.println(aryCoord(this.aryBoat.get(i)).get(0)[1]);*/
                            return aryCoord(this.aryBoat.get(i));
                        }
                    }
                }
            } else {
                for (int j = boat.getX(); j < (boat.getX() + boat.getLongueur()); j++) {
                    if (b.getX() == j && b.getY() == boat.getY()) {
                        this.aryBoat.get(i).setCpt();
                    }

                    if (this.aryBoat.get(i).getCpt() == this.aryBoat.get(i).getLongueur()) {
                        /*System.out.println(aryCoord(this.aryBoat.get(i)).get(0)[0]);
                        System.out.println(aryCoord(this.aryBoat.get(i)).get(0)[1]);*/
                        return aryCoord(this.aryBoat.get(i));
                    }
                }
            }
        }
        return null;
    }

    public List<int[]> aryCoord(Boat b) {

        List<int[]> list = new ArrayList<>();


        if (b.getOrientation() == 0) {
            for (int j = b.getY(); j < (b.getY() + b.getLongueur()); j++) {
                int[] coord = new int[2];
                coord[0] = b.getX();
                coord[1] = j;

                list.add(coord);
            }
        } else {
            for (int j = b.getX(); j < (b.getX() + b.getLongueur()); j++) {
                int[] coord = new int[2];
                coord[0] = j;
                coord[1] = b.getY();

                list.add(coord);
            }
        }

        return list;
    }

    public boolean checkBoats() {
        int cpt = 0;
        for (int i = 0; i < aryBoat.size(); i++) {
            Boat boat = aryBoat.get(i);
            if (boat.getCpt() == boat.getLongueur()) {
                cpt++;
            }
        }

        if (cpt == this.aryBoat.size()) {
            return true;
        }

        return false;
    }

    public void setBox(Box b) {
        if (b.getState() == 0) {
            this.grid[b.getX()][b.getY()].setState(1);
        } else {
            this.grid[b.getX()][b.getY()].setState(0);
        }
    }

    @Override
    public String toString() {
        String s = "";

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                s += " " + this.grid[i][j].getState() + " ";
                //s += "(" + i + "," + j + ")";
            }
            s += "\n";
        }

        return s;
    }

    public String afficheListBoat() {
        String s = "";
        for (int i = 0; i < aryBoat.size(); i++) {
            Boat boat = aryBoat.get(i);
            s += i + " => " + boat.toString() + "\n";
        }
        return s;
    }

    public boolean generateBoat() {


        for (int i = 0; i < this.getH(); i++) {
            for (int j = 0; j < this.getW(); j++) {
                if (this.grid[i][j].getState() == 1) {
                    int iTemp = i;
                    int jTemp = j;

                    Boat boat = new Boat(i, j);

                    this.grid[i][j].setState(2);

                    boolean ok = false;

                    boolean trouve = false;

                    while (!ok) {
                        j++;
                        if (j < 10) {
                            while (j < 10 && this.grid[boat.getX()][j].getState() == 1) {
                                trouve = true;
                                boat.setLongueur(boat.getLongueur() + 1);
                                boat.setOrientation(0);

                                this.grid[boat.getX()][j].setState(2);

                                j++;
                            }
                        }

                        i++;
                        if (i < 10 && !trouve) {
                            while (i < 10 && this.grid[i][boat.getY()].getState() == 1) {

                                boat.setLongueur(boat.getLongueur() + 1);
                                boat.setOrientation(1);

                                this.grid[i][boat.getY()].setState(2);
                                i++;
                            }
                        }

                        boolean bool = this.addBoat(boat);

                        if (!bool) {
                            return false;
                        }

                        ok = true;
                    }

                    i = iTemp;
                    j = jTemp;
                }
            }
        }

        /*String s = "";
        for (int i = 0; i < this.getH(); i++) {
            for (int j = 0; j < this.getW(); j++) {
                s += this.grid[i][j].getState();
            }
            s += "\n";
        }
        System.out.println(s);*/

        boolean verif = verifGrille();

        //System.out.println(verif);

        if (!verif) {
            for (int i = 0; i < this.getH(); i++) {
                for (int j = 0; j < this.getW(); j++) {
                    if (this.grid[i][j].getState() == 2) {
                        this.grid[i][j].setState(1);
                    }
                }
            }

            this.aryBoat.clear();
        }
        return verif;
    }

    private boolean addBoat(Boat boat) {
        if (this.aryBoat.size() < 4) {
            this.aryBoat.add(boat);
            return true;
        }
        return false;
    }

    private boolean verifGrille() {

        int boat2 = 0;
        int boat3 = 0;
        int boat4 = 0;
        int boat5 = 0;

        //System.out.println(this.aryBoat);

        if (this.aryBoat.size() == 4) {
            for (int i = 0; i < this.aryBoat.size(); i++) {
                Boat b = this.aryBoat.get(i);
                switch (b.getLongueur()) {
                    case 2:
                        boat2++;
                        break;
                    case 3:
                        boat3++;
                        break;
                    case 4:
                        boat4++;
                        break;
                    case 5:
                        boat5++;
                        break;
                    default:
                        break;
                }
            }

            /*System.out.println(boat2);
            System.out.println(boat3);
            System.out.println(boat4);
            System.out.println(boat5);*/

            if (boat2 == 1 && boat3 == 1 && boat4 == 1 && boat5 == 1) {
                return true;
            }
        }

        return false;
    }

    public void setAllEtatBox(Grid grid) {
        this.grid = grid.grid;
        System.out.println(this.grid);
    }

    public Boat touche(Box b) {
        for (int i = 0; i < aryBoat.size(); i++) {
            Boat boat = aryBoat.get(i);

            if (boat.getOrientation() == 0) {
                for (int j = boat.getX(); j < boat.getLongueur(); j++) {
                    if (b.getX() == j && b.getY() == boat.getY()) {
                        //System.out.println(boat);
                        return boat;
                    }
                }
            } else {
                for (int j = boat.getY(); j < boat.getLongueur(); j++) {
                    if (b.getY() == j && b.getX() == boat.getX()) {
                        //System.out.println(boat);
                        return boat;
                    }
                }
            }
        }

        return null;
    }

}
