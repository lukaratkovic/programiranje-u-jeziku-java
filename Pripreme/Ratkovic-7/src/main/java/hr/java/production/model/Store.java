package hr.java.production.model;


import java.util.Objects;
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
    public Store(String name, String webAddress, Set<Item> items, Long id) {
        super(name, id);
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Store)) return false;
        if (!super.equals(o)) return false;
        Store store = (Store) o;
        return Objects.equals(webAddress, store.webAddress) && Objects.equals(items, store.items);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), webAddress, items);
    }

    @Override
    public String toString() {
        return "Store{" +
                "name='" + name + '\'' +
                ", id=" + id +
                ", webAddress='" + webAddress + '\'' +
                ", items=" + items +
                '}';
    }

    public String getItemsAsString(){
        if(items.size() == 0) return "";
        String ret = "";
        for(Item i : items){
            ret += i.getName() + ", ";
        }
        return ret.substring(0, ret.length() - 2);
    }
}
