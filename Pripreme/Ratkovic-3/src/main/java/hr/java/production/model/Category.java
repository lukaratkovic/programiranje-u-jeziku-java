package hr.java.production.model;

public class Category extends NamedEntity {
    String description;

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

    public boolean equals(Category compareCategory) {
        return this.name.equals(compareCategory.name) && this.description.equals(compareCategory.description);
    }
}
