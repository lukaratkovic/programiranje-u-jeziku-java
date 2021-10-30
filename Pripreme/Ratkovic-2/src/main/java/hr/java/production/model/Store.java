package hr.java.production.model;

public class Store {
    String name, webAddress;
    Item[] items;

    public Store(String name, String webAddress, Item[] items) {
        setName(name);
        this.webAddress = webAddress;
        this.items = items;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if(name=="") name="Default Store";
        this.name = name;
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
