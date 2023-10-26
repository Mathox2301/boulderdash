package boulderDash.model;

/**
 *
 * @author lejeu
 */
public class CommandDeplacements implements Command {

    private  Level oldBoard;
    private  BoulderDash bd;

    /**
     * Construit un objet de type CommandDeplacements
     *
     * @param bd un BoulderDash
     */
    public CommandDeplacements(BoulderDash bd) {
        this.bd = bd;
        oldBoard = new Level(bd.getNbLvl());
        for (int i = 0; i < bd.getLvl().getBoard().length; i++) {
            for (int j = 0; j < bd.getLvl().getBoard()[0].length; j++) {
                Position pos = new Position(i, j);
                oldBoard.setCase(pos, bd.getLvl().getCase(pos).getTypeCase());
            }

        }
    }

    /**
     * execute une commande
     */
    @Override
    public void execute() {
        for (int i = 0; i < bd.getLvl().getBoard().length; i++) {
            for (int j = 0; j < bd.getLvl().getBoard()[0].length; j++) {
                Position pos = new Position(i, j);
                if (!oldBoard.getCase(pos).getTypeCase().equals(bd.getLvl().getCase(pos).getTypeCase())) {
                    bd.getLvl().setCase(pos, oldBoard.getCase(pos).getTypeCase());
                }
            }
        }
        bd.setNbDiamondsRestants(bd.nbDiamondLvl());
    }

    /**
     * annule une commande
     */
    @Override
    public void undo() {
        for (int i = 0; i < bd.getLvl().getBoard().length; i++) {
            for (int j = 0; j < bd.getLvl().getBoard()[0].length; j++) {
                Position pos = new Position(i, j);
                if (!oldBoard.getCase(pos).getTypeCase().equals(bd.getLvl().getCase(pos).getTypeCase())) {
                    bd.getLvl().setCase(pos, oldBoard.getCase(pos).getTypeCase());

                }

            }
        }
        bd.setNbDiamondsRestants(bd.nbDiamondLvl());

    }

    /**
     * Getter pour l'attribut oldBoard
     *
     * @return un tableau de tableau de type Case
     */
    @Override
    public Level getOldBoard() {
        return oldBoard;
    }

}
