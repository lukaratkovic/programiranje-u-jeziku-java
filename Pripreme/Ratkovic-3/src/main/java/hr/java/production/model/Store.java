package hr.java.production.model;


/**
 * Represents a store with a physical address and a list of articles
 */
public class Store extends NamedEntity {
    String webAddress;
    Item[] items;

    /**
     * Constructor for Store
     *
     * @param name       Store name
     * @param webAddress Store web address (domain)
     * @param items      Item[] list
     */
    public Store(String name, String webAddress, Item[] items) {
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

    public Item[] getItems() {
        return items;
    }

    public void setItems(Item[] items) {
        this.items = items;
    }
}
