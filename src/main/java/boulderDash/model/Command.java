package boulderDash.model;

/**
 *
 * @author lejeu
 */
public interface Command {

    /**
     * execute une commande
     */
    public void execute();
    
    /**
     * annule une commande
     */
    public void undo();
    /**
     * Getter pour l'attribut oldBoard
     * @return un tableau de tableau de type Case
     */
    public Level getOldBoard();
}
