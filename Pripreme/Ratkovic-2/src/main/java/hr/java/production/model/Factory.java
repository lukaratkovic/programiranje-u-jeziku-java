package hr.java.production.model;

public class Factory {
    String name;
    Address address;
    Item[] items;

    public Factory(String name, Address address, Item[] items) {
        setName(name);
        this.address = address;
        this.items = items;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if(name=="") name="Default Factory";
        this.name = name;
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
