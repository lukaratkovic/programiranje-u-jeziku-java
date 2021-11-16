package hr.java.production.model;


import java.util.Objects;

/**
 * Represents a category of items
 */
public class Category extends NamedEntity {
    String description;

    /**
     * Constructor for Category
     *
     * @param name        Category name
     * @param description Category description
     */
    public Category(String name, String description) {
        super(name);
        this.setDescription(description);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Compares two categories
     *
     * @param compareCategory category to be compared to
     * @return result of comparison between two objects
     */
    public boolean equals(Category compareCategory) {
        return this.name.equals(compareCategory.name) && this.description.equals(compareCategory.description);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Category)) return false;
        if (!super.equals(o)) return false;
        Category category = (Category) o;
        return Objects.equals(description, category.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), description);
    }
}
