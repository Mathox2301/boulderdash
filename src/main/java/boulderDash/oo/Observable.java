package boulderDash.oo;

/**
 *
 * @author lejeu
 */
public interface Observable {
    
    /**
     * ajoute un Observer à la liste des Observer
     *
     * @param observer un Observer
     */
    void addObserver(Observer observer);
    
    /**
     * retire un Observer à la liste des Observer
     *
     * @param observer un Observer
     */
    void removeObserver(Observer observer);
    
    /**
     * notifie les Observer
     */
    void notifyObservers(); 
    
    
    
}
