package hr.java.production.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Store extends NamedEntity {
    String webAddress;
    Item[] items;
    private static final Logger logger = LoggerFactory.getLogger(Category.class);

    public Store(String name, String webAddress, Item[] items) {
        super(name);
        this.webAddress = webAddress;
        this.items = items;
        logger.info("Object of class Store was created");
    }

    public String getWebAddress() {
        return webAddress;
    }

    public void setWebAddress(String webAddress) {
        this.webAddress = webAddress;
    }

    public Item[] getItems() {
        return items;
    }

    public void setItems(Item[] items) {
        this.items = items;
    }
}
