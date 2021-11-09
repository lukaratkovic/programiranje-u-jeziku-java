package hr.java.production.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Factory extends NamedEntity {
    Address address;
    Item[] items;
    private static final Logger logger = LoggerFactory.getLogger(Factory.class);

    public Factory(String name, Address address, Item[] items) {
        super(name);
        this.address = address;
        this.items = items;
        logger.info("Object of class Factory was created");
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Item[] getItems() {
        return items;
    }

    public void setItems(Item[] items) {
        this.items = items;
    }
}
