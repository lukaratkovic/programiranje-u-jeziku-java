package hr.java.production.model;


/**
 * Represents a factory with a physical address and list of articles
 */
public class Factory extends NamedEntity {
    Address address;
    Item[] items;

    /**
     * Constructor for Factory
     *
     * @param name    Factory name
     * @param address Factory address
     * @param items   List of factory items
     */
    public Factory(String name, Address address, Item[] items) {
        super(name);
        this.address = address;
        this.items = items;
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
