package hr.java.production.model;


import java.util.Set;

/**
 * Represents a store with a physical address and a list of articles
 */
public class Store extends NamedEntity {
    String webAddress;
    Set<Item> items;

    /**
     * Constructor for Store
     *
     * @param name       Store name
     * @param webAddress Store web address (domain)
     * @param items      Item[] list
     */
    public Store(String name, String webAddress, Set<Item> items) {
        super(name);
        this.webAddress = webAddress;
        this.items = items;
    }

    public String getWebAddress() {
        return webAddress;
    }

    public void setWebAddress(String webAddress) {
        this.webAddress = webAddress;
    }

    public Set<Item> getItems() {
        return items;
    }

    public void setItems(Set<Item> items) {
        this.items = items;
    }
}
