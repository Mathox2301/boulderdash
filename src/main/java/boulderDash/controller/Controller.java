package boulderDash.controller;

import boulderDash.model.Façade;
import boulderDash.view.View;

/**
 *
 * @author lejeu
 */
public class Controller {

    private final View v;
    private final Façade bd;
    /**
     * Construit un objet de type Controller
     */
    public Controller() {
        this.bd = new Façade();
        this.v = new View(bd);
    }
    /**
     * lance le jeu et s'occupe de lancer les différentes méthodes
     */
    public void play() {
        boolean isDeadPlayer = false;
        while (!isDeadPlayer && bd.nbLvl() < 3) {
            boolean isGameFinished = false;
            while (!isGameFinished) {
                v.displayLevel();
                v.askDeplacement();
                bd.fallRocks();
                boolean isFinish;
                do {
                    isFinish = bd.slipRocks();
                } while (isFinish == true);
                isGameFinished = bd.isGameFinished();
            }
            if (bd.isGameWin()) {
                bd.newLvl(bd.nbLvl() + 1);
            } else {
                isDeadPlayer = true;
            }
        }
        System.out.println("Vous etes mort !");
        System.out.println("Fin du jeu");

    }

}
