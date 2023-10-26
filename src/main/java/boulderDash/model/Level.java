package boulderDash.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author lejeu
 */
public class Level {

    private Case[][] board;
    private Position posPlayer;

    /**
     * Construit un objet de Level
     *
     * @param i un entier
     */
    public Level(int i) {
        this.board = new Case[26][78];
        board = setLvl(i);

    }

    /**
     * Construit un nouveau plateau
     *
     * @param i le numéro du niveau
     * @return un tableau de tableau de type Case
     */
    public Case[][] setLvl(int i) {
        StringBuffer sb;
        String lvl = "";
        String line;
        int lg = 0;
        int col = 0;
        try {
            File file = new File("src\\main\\levels\\level_" + i + ".txt");
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            sb = new StringBuffer();
            while ((line = br.readLine()) != null) {
                sb.append(line);
                if (lg == 0) {
                    col = line.length();
                }
                lg++;
            }

            lvl = sb.toString();
            fr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        int cpt = 0;
        board = new Case[26][78];
        for (int j = 0; j < board.length; j++) {
            for (int k = 0; k < board[0].length; k++) {
                switch (lvl.charAt(cpt)) {
                    case 'e':
                        board[j][k] = new Case(TypeCase.MUD);
                        break;
                    case 'b':
                        board[j][k] = new Case(TypeCase.WALL);
                        break;
                    case 'r':
                        board[j][k] = new Case(TypeCase.ROCK);
                        break;
                    case 'm':
                        this.posPlayer = new Position(j, k);
                        board[j][k] = new Case(TypeCase.PLAYER);
                        break;
                    case 'd':
                        board[j][k] = new Case(TypeCase.DIAMOND);
                        break;
                    case 'l':
                        board[j][k] = new Case(TypeCase.PORTAL);
                        break;
                    default:
                        board[j][k] = new Case(TypeCase.EMPTY);
                        break;
                }
                cpt++;

            }
        }
        return board;
    }

    /**
     * Getter pour l'attribut board
     *
     * @return le plateau
     */
    public Case[][] getBoard() {
        return board;
    }

    /**
     * Donne un type de case à une position donnée
     *
     * @param pos une position
     * @param tc un type de case
     */
    public void setCase(Position pos, TypeCase tc) {
        board[pos.getX()][pos.getY()].setTypeCase(tc);

    }

    /**
     * Getter pour une case du plateau en position donnée
     *
     * @param pos une position
     * @return une case du plateau
     */
    public Case getCase(Position pos) {
        return board[pos.getX()][pos.getY()];

    }

    /**
     * Getter pour toutes les position des cases de type ROCK et DIAMOND
     *
     * @return une liste de positions
     */
    public List<Position> allRockCases() {
        List<Position> rocks = new ArrayList();
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j].getTypeCase().equals(TypeCase.DIAMOND) || board[i][j].getTypeCase().equals(TypeCase.ROCK)) {
                    rocks.add(new Position(i, j));
                }
            }

        }
        return rocks;
    }

    /**
     * Getter pour toutes les position des cases de type ROCK
     *
     * @return une liste de positions
     */
    public List<Position> allStonesCases() {
        List<Position> rocks = new ArrayList();
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j].getTypeCase().equals(TypeCase.ROCK)) {
                    rocks.add(new Position(i, j));
                }
            }

        }
        return rocks;
    }

    /**
     * Getter pour toutes les position des cases de type DIAMOND
     *
     * @return une liste de positions
     */
    public List<Position> allDiamondCases() {
        List<Position> diamants = new ArrayList();
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j].getTypeCase().equals(TypeCase.DIAMOND)) {
                    diamants.add(new Position(i, j));
                }
            }

        }
        return diamants;
    }

    /**
     * Getter pour la position de la case de type PLAYER
     *
     * @return une position
     */
    public Position posPlayer() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j].getTypeCase().equals(TypeCase.PLAYER)) {
                    Position pos = new Position(i, j);
                    return pos;
                }
            }

        }
        return null;
    }
    /**
     * Getter pour la position de la case de type PORTAL
     * @return 
     */
    public Position posEndLvl() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j].getTypeCase().equals(TypeCase.PORTAL)) {
                    Position pos = new Position(i, j);
                    return pos;
                }
            }

        }
        return null;
    }

    /**
     * Vérifie si la case en position donnée est de type EMPTY
     *
     * @param pos une position
     * @return true si la case est de ce type, faux sinon
     */
    public boolean isEmpty(Position pos) {
        return getCase(pos).getTypeCase().equals(TypeCase.EMPTY);
    }

}
