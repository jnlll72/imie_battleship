import java.io.Serializable;

public class Boat implements Serializable{
    private int longueur;
    private int x;
    private int y;
    private int orientation; // O = horizontale, 1 = verticale
    private boolean etat;

    public Boat(int longueur, int x, int y, int orientation) {
        this.longueur = longueur;
        this.x = x;
        this.y = y;
        this.orientation = orientation;
        this.etat = true;
    }

    public Boat(int x, int y) {
        this.longueur = 1;
        this.x = x;
        this.y = y;
        this.orientation = 0;
        this.etat = true;
    }

    public boolean isEtat() {
        return etat;
    }

    public void setEtat(boolean etat) {
        this.etat = etat;
    }

    public int getLongueur() {
        return longueur;
    }

    public void setLongueur(int longueur) {
        this.longueur = longueur;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getOrientation() {
        return orientation;
    }

    public void setOrientation(int orientation) {
        this.orientation = orientation;
    }

    @Override
    public String toString() {
        return "Boat{" +
                "longueur=" + longueur +
                ", x=" + x +
                ", y=" + y +
                ", orientation=" + orientation +
                '}';
    }
}
