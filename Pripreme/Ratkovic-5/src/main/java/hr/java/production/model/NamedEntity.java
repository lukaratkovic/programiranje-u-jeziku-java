package hr.java.production.model;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NamedEntity)) return false;
        NamedEntity that = (NamedEntity) o;
        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
