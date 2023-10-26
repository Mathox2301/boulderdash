package boulderDash.model;

/**
 *
 * @author lejeu
 */
public enum Direction {
    NORTH(-1, 0), SOUTH(1, 0), EAST(0, 1), WEST(0, -1);
    private final int deltaX;
    private final int deltaY;

    /**
     * Construit un Objet de type Direction
     *
     * @param deltaR un entier
     * @param deltaC un entier
     */
    private Direction(int deltaR, int deltaC) {
        this.deltaX = deltaR;
        this.deltaY = deltaC;
    }

    /**
     * Getter pour l'attribut deltaX
     *
     * @return un entier représentant deltaX
     */
    public int getDeltaX() {
        return deltaX;
    }

    /**
     * Getter pour l'attribut deltaY
     *
     * @return un entier représentant deltaY
     */
    public int getDeltaY() {
        return deltaY;
    }


}
