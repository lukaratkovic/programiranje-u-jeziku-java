package hr.java.production.model;

import java.util.Objects;

/**
 * Represents any entity with a name
 */
public abstract class NamedEntity {
    String name;
    Long id;

    /**
     * Constructor for NamedEntity
     *
     * @param name
     * @param id
     */
    public NamedEntity(String name, Long id) {
        this.name = name;
        this.id = id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
