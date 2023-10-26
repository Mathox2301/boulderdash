package boulderDash.model;

import java.util.List;
import java.util.Stack;

/**
 *
 * @author lejeu
 */
public class BoulderDash {

    private final Level lvl;
    private Position playerPosition;
    private Position endLvlPosition;
    private int nbDiamonds;
    private int nbDiamondsRestants;
    private int nbLvl;
    private final Stack<Command> commandRedo;
    private final Stack<Command> commandUndo;
    private int tailleMaxRedo;
    private int tailleMaxUndo;
    
    /**
     * Construit un objet de type BoulderDash
     * @param i un entier représentant le numéro du niveau
     */
    public BoulderDash(int i) {
        this.nbLvl = 1;
        this.lvl = new Level(nbLvl);
        this.playerPosition = lvl.posPlayer();
        this.endLvlPosition = lvl.posEndLvl();
        this.nbDiamonds = nbDiamondLvl();
        this.nbDiamondsRestants = nbDiamondLvl();
        this.commandRedo = new Stack();
        this.commandUndo = new Stack();
        tailleMaxRedo = getCommandRedo().size();
        tailleMaxUndo = getCommandUndo().size();

    }

    /**
     * Compte le nombre de diamants encore disponible dans la partie
     *
     * @return le nombre de diamants restants
     */
    public int nbDiamondLvl() {
        int cpt = 0;
        List<Position> rocks = lvl.allRockCases();
        for (int i = 0; i < rocks.size(); i++) {
            if (lvl.getCase(rocks.get(i)).getTypeCase().equals(TypeCase.DIAMOND)) {
                cpt++;
            }
        }
        return cpt;
    }

    /**
     * Construit un nouveau niveau et instancie ses attributs
     *
     * @param i le numéro du niveau
     */
    public void setNewLevel(int i) {
        this.nbLvl = i;
        lvl.setLvl(nbLvl);
        this.playerPosition = lvl.posPlayer();
        this.endLvlPosition = lvl.posEndLvl();
        this.nbDiamonds = nbDiamondLvl();
        this.nbDiamondsRestants = nbDiamondLvl();
        this.commandRedo.clear();
        this.commandUndo.clear();

    }

    /**
     * Getter pour l'attribut lvl
     *
     * @return le niveau
     */
    public Level getLvl() {
        return lvl;
    }

    /**
     * Getter pour l'attribut playerPosition
     *
     * @return la position du joueur
     */
    public Position getPlayerPosition() {
        return lvl.posPlayer();
    }

    /**
     * Getter pour l'attribut nbDiamonds
     *
     * @return le nombre de diamants total du niveau
     */
    public int getNbDiamonds() {
        return nbDiamonds;
    }

    /**
     * Getter pour l'attribut nbDiamonds
     *
     * @return le nombre de diamants restants dans le niveau
     */
    public int getNbDiamondsRestants() {
        return nbDiamondsRestants;
    }
    /**
     * setter pour l'attribut nbDiamonds Restants
     * @param i un entier
     */
    public void setNbDiamondsRestants(int i) {
        this.nbDiamondsRestants = i;
    }

    /**
     * Bouge le player dans une position donnée
     *
     * @param dir une direction
     * 
     */
    public void movePlayer(Direction dir) {

        Position pos = getPlayerPosition();
        if (isMovePossible(pos, dir)) {
            Command c = new CommandDeplacements(this);
            commandUndo.push(c);
            lvl.setCase(pos, TypeCase.EMPTY);
            pos = pos.move(dir);
            if (lvl.getCase(pos).getTypeCase().equals(TypeCase.ROCK)) {
                Position pos2 = pos.move(dir);
                lvl.setCase(pos2, TypeCase.ROCK);
            } else if (lvl.getCase(pos).getTypeCase().equals(TypeCase.DIAMOND)) {
                nbDiamondsRestants--;
            }
            lvl.setCase(pos, TypeCase.PLAYER);
            playerPosition = pos;
            commandRedo.clear();
        }
    }

    /**
     * Vérifie si un mouvement du joueur est possible dans une direction donnée
     *
     * @param pos une position
     * @param dir une direction
     * @return true si le mouvement est possible, faux sinon
     */
    public boolean isMovePossible(Position pos, Direction dir) {
        Position p = new Position(pos.getX() + dir.getDeltaX(), pos.getY() + dir.getDeltaY());
        Position p2 = new Position(p.getX() + dir.getDeltaX(), p.getY() + dir.getDeltaY());
        if (lvl.getCase(p).getTypeCase().equals(TypeCase.WALL)
                || (lvl.getCase(p).getTypeCase().equals(TypeCase.PORTAL) && (nbDiamonds-nbDiamondsRestants) < (nbDiamonds / 4))) {
            System.out.println("mouvement impossible" + lvl.getCase(p).toString());
            return false;
        } else if (lvl.getCase(p).getTypeCase().equals(TypeCase.ROCK)) {
            if (lvl.getCase(p2).getTypeCase().equals(TypeCase.EMPTY) && !dir.equals(Direction.NORTH) && !dir.equals(Direction.SOUTH)) {
                return true;
            } else {
                System.out.println("mouvement impossible" + lvl.getCase(p).toString());
                return false;
            }
        }
        return true;
    }

    /**
     * Fait glisser les pierres et les diamants qui se trouvent sur d'autres
     * pierres ou diamants
     *
     * @return true si les pierres n'ont pas glissé, faux sinon
     */
    public boolean slipRocks() {
        boolean isFinish = true;
        Position posLeft;
        Position posRight;
        Position posDown;
        Position underRight;
        Position underLeft;
        Position currentPos;
        TypeCase tp;
        List<Position> posRocks = lvl.allRockCases();
        for (Position val : posRocks) {
            tp = lvl.getCase(val).getTypeCase();
            currentPos = val;
            posLeft = new Position(val.getX() + Direction.WEST.getDeltaX(), val.getY() + Direction.WEST.getDeltaY());
            posRight = new Position(val.getX() + Direction.EAST.getDeltaX(), val.getY() + Direction.EAST.getDeltaY());
            posDown = new Position(val.getX() + Direction.SOUTH.getDeltaX(), val.getY() + Direction.SOUTH.getDeltaY());

            if (lvl.getCase(posDown).getTypeCase().equals(TypeCase.DIAMOND) || lvl.getCase(posDown).getTypeCase().equals(TypeCase.ROCK)) {
                underLeft = new Position(posLeft.getX() + Direction.SOUTH.getDeltaX(), posLeft.getY() + Direction.SOUTH.getDeltaY());
                underRight = new Position(posRight.getX() + Direction.SOUTH.getDeltaX(), posRight.getY() + Direction.SOUTH.getDeltaY());

                if (lvl.isEmpty(posLeft)) {
                    if (lvl.isEmpty(underLeft)) {
                        currentPos = posLeft;
                        lvl.setCase(val, TypeCase.EMPTY);
                        lvl.setCase(currentPos, tp);
                        isFinish = false;

                    }

                } else if (lvl.isEmpty(posRight)) {
                    if (lvl.isEmpty(underRight)) {
                        currentPos = posRight;
                        lvl.setCase(val, TypeCase.EMPTY);
                        lvl.setCase(currentPos, tp);
                        isFinish = false;

                    }
                }

            }

        }
        fallRocks();
        return isFinish;
    }

    /**
     * Fait tomber les pierres et les diamants
     */
    public void fallRocks() {
        Position pos;
        TypeCase tp;
        List<Position> posRocks = lvl.allRockCases();
        for (Position val : posRocks) {
            tp = lvl.getCase(val).getTypeCase();
            pos = new Position(val.getX() + Direction.SOUTH.getDeltaX(), val.getY() + Direction.SOUTH.getDeltaY());
            Position pos2 = pos;
            while (lvl.isEmpty(pos)) {
                pos = pos.move(Direction.SOUTH);
            }
            if (!pos.equals(pos2) && !lvl.getCase(pos).getTypeCase().equals(TypeCase.PLAYER)) {
                pos = pos.move(Direction.NORTH);
                lvl.setCase(val, TypeCase.EMPTY);
                lvl.setCase(pos, tp);

            }
            if (!pos.equals(pos2) && lvl.getCase(pos).getTypeCase().equals(TypeCase.PLAYER)) {
                lvl.setCase(val, TypeCase.EMPTY);
                lvl.setCase(pos, tp);
                fallRocks();
            }

        }
    }

    /**
     * Vérifie si le joueur est mort
     *
     * @return true si le joueur est mort, faux sinon
     */
    public boolean isPlayerDead() {
        return getPlayerPosition() == null;

    }

    /**
     * Vérifie si le jeu est fini
     *
     * @return true si le jeu est fini, faux sinon
     */
    public boolean isGameFinished() {
        return lvl.getCase(endLvlPosition).getTypeCase().equals(TypeCase.PLAYER);
    }

    /**
     * Getter pour l'attribut nbLvl
     *
     * @return le numéro du niveau
     */
    public int getNbLvl() {
        return nbLvl;
    }

    /**
     * Getter pour l'attribut endLvlPosition
     *
     * @return la position de la sortie du niveau
     */
    public Position getEndLvlPosition() {
        return endLvlPosition;
    }

    /**
     * Getter pour l'attribut commandRedo
     *
     * @return une pile de Type Command
     */
    public Stack<Command> getCommandRedo() {
        return commandRedo;
    }

    /**
     * Getter pour l'attribut commandUndo
     *
     * @return une pile de Type Command
     */
    public Stack<Command> getCommandUndo() {
        return commandUndo;
    }

    /**
     * defait un mouvement 
     */
    public void undo() {
        if (!getCommandUndo().empty() && tailleMaxUndo == getCommandUndo().size()) {
            Command c = getCommandUndo().pop();
        }
        if (getCommandRedo().empty()) {
            Command d = new CommandDeplacements(this);
            getCommandRedo().push(d);
        }
        if (!getCommandUndo().empty()) {
            Command c = getCommandUndo().pop();
            getCommandRedo().push(c);
            this.tailleMaxRedo = getCommandRedo().size();
            c.undo();
        }
    }
    /**
     * refait un mouvement defait
     */
    public void redo() {
        if (!getCommandRedo().empty() && getCommandRedo().size() == tailleMaxRedo) {
            Command c = getCommandRedo().pop();
        }
        if (getCommandUndo().empty()) {
            Command d = new CommandDeplacements(this);
            getCommandUndo().push(d);
        }
        if (!getCommandRedo().empty()) {
            Command c = getCommandRedo().pop();
            getCommandUndo().push(c);
            this.tailleMaxUndo = getCommandUndo().size();
            c.execute();

        }

    }

}
