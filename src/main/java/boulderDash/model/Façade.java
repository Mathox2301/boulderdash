package boulderDash.model;

import boulderDash.oo.Observable;
import boulderDash.oo.Observer;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 *
 * @author lejeu
 */
public class Façade implements Observable {

    private final BoulderDash bd;
    private final List<Observer> observers = new ArrayList<>();
    private int tailleMaxRedo;
    private int tailleMaxUndo;
    
    /**
     * Construit un objet de type Façade
     */
    public Façade() {
        bd = new BoulderDash(1);

    }

    /**
     * Getter pour le nombre de diamants total du niveau
     *
     * @return le nombre de diamants total du niveau
     */
    public int nbDiamondLvl() {
        return bd.getNbDiamonds();
    }

    /**
     * Getter pour le numéro du niveau
     *
     * @return
     */
    public int nbLvl() {
        return bd.getNbLvl();
    }

    /**
     * Fait glisser les pierres et les diamants qui se trouvent sur d'autres
     * pierres ou diamants
     *
     * @return true si des pierres ont glissées, faux sinon
     */
    public boolean slipRocks() {
        return bd.slipRocks();
    }

    /**
     * Fait tomber les pierres et les diamants
     *
     */
    public void fallRocks() {
        bd.fallRocks();
    }

    /**
     * Getter pour le nombre de diamants restants dans le niveau
     *
     * @return le nombre de diamants restants dans le niveau
     */
    public int nbRemainingDiamond() {
        return bd.getNbDiamondsRestants();
    }

    /**
     * Construit un nouveau niveau et instancie ses attributs
     *
     * @param i le numéro du niveau
     */
    public void newLvl(int i) {
        bd.setNewLevel(i);
        notifyObservers();
    }

    /**
     * Vérifie si le jeu est fini
     *
     * @return true si la partie est fini, faux sinon
     */
    public boolean isGameFinished() {
        boolean isGameFinished = bd.isGameFinished();
        if (isGameFinished == false) {
            isGameFinished = bd.isPlayerDead();
        }
        return isGameFinished;
    }

    /**
     * Vérifie si la partie est remportée
     *
     * @return true si la partie est remportée, faux sinon
     */
    public boolean isGameWin() {
        return bd.isGameFinished();
    }

    /**
     * Bouge le joueur dans une direction donnée
     *
     * @param dir une direction
     */
    public void movePlayer(Direction dir) {
        bd.movePlayer(dir);
    }

    /**
     * defait un deplacement
     */
    public void undo() {
        bd.undo();

    }

    /**
     * refait un deplacement defait
     */
    public void redo() {
        bd.redo();
    }

    /**
     * permet d'abandonner la partie
     */
    public void giveUp() {
        bd.getLvl().setCase(posPlayer(), TypeCase.ROCK);
    }

    /**
     * Getter pour la hauteur du niveau
     *
     * @return la hauiteur du niveau
     */
    public int heightLvl() {
        return bd.getLvl().getBoard().length;
    }

    /**
     * Getter pour la longueur du niveau
     *
     * @return la longueur du jeu
     */
    public int widthLvl() {
        return bd.getLvl().getBoard()[0].length;
    }

    /**
     * Getter pour le type d'une case en position donnée
     *
     * @param pos une position
     * @return le type d'une case
     */
    public TypeCase getTypeCase(Position pos) {
        return bd.getLvl().getCase(pos).getTypeCase();
    }

    /**
     * Getter pour la position du joueur
     *
     * @return la position du joueur
     */
    public Position posPlayer() {
        return bd.getPlayerPosition();
    }

    /**
     * Getter pour le niveau du jeu
     *
     * @return le niveau du jeu
     */
    public Level getLvl() {
        return bd.getLvl();

    }

    /**
     * ajoute un Observer à la liste des Observer
     *
     * @param observer un Observer
     */
    @Override
    public void addObserver(Observer observer) {
        if (!observers.contains(observer)) {
            System.out.println("BoulderDash: add 1 observer (total "
                    + observers.size() + " )");
            observers.add(observer);
        }
    }

    /**
     * retire un Observer à la liste des Observer
     *
     * @param observer un Observer
     */
    @Override
    public void removeObserver(Observer observer) {
        System.out.println("BoulderDash: remove 1 observer (total "
                + observers.size() + " )");

        observers.remove(observer);
    }

    /**
     * notifie les Observer
     */
    @Override
    public void notifyObservers() {
        System.out.println("BoulderDash: notify "
                + observers.size()
                + " observers");

        for (Observer observer : observers) {
            observer.update();

        }
    }

    /**
     * Getter pour la liste de commandes de Redo
     *
     * @return une pile de Type Command
     */
    public Stack getCommandUndo() {
        return bd.getCommandUndo();
    }

    /**
     * Getter pour l'attribut bd
     *
     * @return un BoulderDash
     */
    public BoulderDash getBd() {
        return bd;
    }
    /**
     * Verifie si un mouvement est possible ou pas
     * @param pos une Position
     * @param dir une Direction
     * @return true si le mouvement est possibme, false sinon
     */
    public boolean isPossibleMove(Position pos, Direction dir){
        return bd.isMovePossible(pos, dir);
    }

}
