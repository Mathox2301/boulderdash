package boulderDash.view;

import boulderDash.model.*;
import java.util.Scanner;

/**
 *
 * @author lejeu
 */
public class View {

    private final Façade bd;
    /**
     * Construit un objet de type View
     * @param bd une façade
     */
    public View(Façade bd) {
        this.bd = bd;
    }
    /**
     * Affiche le jeu en mode console 
     */
    public void displayLevel() {
        int nbDiamond =  bd.nbDiamondLvl() - bd.nbRemainingDiamond();
        System.out.println("diamants collectés : " + nbDiamond);
        System.out.println("Diamants necessaires pour ouvrir le portail : " + (bd.nbDiamondLvl() / 4));
        for (int i = 0; i < bd.heightLvl(); i++) {
            for (int j = 0; j < bd.widthLvl(); j++) {
                Position pos = new Position(i, j);
                switch (bd.getTypeCase(pos)) {
                    case ROCK -> System.out.print("r ");
                    case DIAMOND -> System.out.print("d ");
                    case MUD -> System.out.print("m ");
                    case PLAYER -> System.out.print("p ");
                    case PORTAL -> System.out.print("0 ");
                    case WALL -> System.out.print("w ");
                    default -> System.out.print(". ");
                }
            }
            System.out.println();

        }
        System.out.println();
    }
    /**
     * Assigne un String à une méthode
     */
    public void askDeplacement() {
        System.out.println("entrer un deplacement : z,q,s,d,u,r");
        Scanner kbd = new Scanner(System.in);
        String s = kbd.next();
            switch (s) {
                case "z" ->
                    bd.movePlayer(Direction.NORTH);
                case "s" ->
                    bd.movePlayer(Direction.SOUTH);
                case "q" ->
                    bd.movePlayer(Direction.WEST);
                case "d" ->
                    bd.movePlayer(Direction.EAST);
                case "u" ->
                    bd.undo();
                case "r" ->
                    bd.redo();
                default->
                    System.out.println("ce n'est pas une commande");
            }

        
    }

}
