package hr.java.production.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Represents a category of items
 */
public class Category extends NamedEntity {
    String description;
    private static final Logger logger = LoggerFactory.getLogger(Category.class);

    /**
     * Constructor for Category
     *
     * @param name        Category name
     * @param description Category description
     */
    public Category(String name, String description) {
        super(name);
        this.setDescription(description);
        logger.info("Object of class Category was created");
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
}
