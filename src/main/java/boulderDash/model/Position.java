package boulderDash.model;

/**
 *
 * @author lejeu
 */
public class Position {

    private final int x;
    private final int y;

    /**
     * Construit un objet de type Position
     *
     * @param x un entier
     * @param y un entier
     */
    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * getter pour l'attribut x
     *
     * @return un entier représentant la valeur de x
     */
    public int getX() {
        return x;
    }

    /**
     * getter pour l'attribut y
     *
     * @return un entier représentant la valeur de y
     */
    public int getY() {
        return y;
    }

    /**
     * bouge une position dans une direction donnée
     *
     * @param dir une direction
     * @return une position apres déplacement
     */
    public Position move(Direction dir) {
        int lg = this.getY();
        int col = this.getX();
        int lgDir = dir.getDeltaY();
        int colDir = dir.getDeltaX();
        Position pos = new Position(col + colDir, lg + lgDir);
        return pos;

    }

    /**
     * vérifie que deux position sont égales
     *
     * @param pos une position
     * @return true si les 2 positions sont les memes, faux sinon
     */
    @Override
    public boolean equals(Object pos) {
        if (this == pos) {
            return true;
        }
        if (pos == null) {
            return false;
        }
        if (getClass() != pos.getClass()) {
            return false;
        }
        final Position other = (Position) pos;
        if (this.x != other.x) {
            return false;
        }
        return this.y == other.y;
    }

    /**
     * affiche sous la forme d'un String, une position
     *
     * @return un String qui représete la position
     */
    @Override
    public String toString() {
        return "Position{" + "x=" + y + ", y=" + x + '}';
    }

}
