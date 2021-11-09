package hr.java.production.model;

/**
 * Represents any entity with a name
 */
public abstract class NamedEntity {
    String name;

    /**
     * Constructor for NamedEntity
     *
     * @param name name of NamedEntity
     */
    public NamedEntity(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
