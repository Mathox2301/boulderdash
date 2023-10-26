package boulderDash.model;

/**
 *
 * @author lejeu
 */
public class Case {
    private TypeCase typeCase;

    public Case(TypeCase typeCase) {
        this.typeCase = typeCase;
    }
    /**
     * Getter pour l'attribut typeCase
     * @return un TypeCase représentant typeCase
     */
    public TypeCase getTypeCase() {
        return typeCase;
    }
    /**
     * Setter pour l'attribut typeCase
     * @param typeCase un type de case
     */
    public void setTypeCase(TypeCase typeCase) {
        this.typeCase = typeCase;
    }

    @Override
    public String toString() {
        return "Case{" + "typeCase=" + typeCase + '}';
    }

      
    
}
