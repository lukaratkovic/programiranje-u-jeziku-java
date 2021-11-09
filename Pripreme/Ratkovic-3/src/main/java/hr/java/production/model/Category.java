package hr.java.production.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Category extends NamedEntity {
    String description;
    private static final Logger logger = LoggerFactory.getLogger(Category.class);

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

    public boolean equals(Category compareCategory) {
        return this.name.equals(compareCategory.name) && this.description.equals(compareCategory.description);
    }
}
