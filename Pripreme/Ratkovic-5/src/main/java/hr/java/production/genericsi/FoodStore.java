package hr.java.production.genericsi;

import hr.java.production.model.Edible;
import hr.java.production.model.Item;
import hr.java.production.model.Store;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FoodStore<T extends Edible> extends Store {
    List<T> itemsList;

    public FoodStore(String name, String webAddress, List<T> itemsList) {
        super(name, webAddress, new HashSet<>());
        this.itemsList = itemsList;
    }

    public List<T> getItemsList() {
        return itemsList;
    }

    public void setItemsList(List<T> itemsList) {
        this.itemsList = itemsList;
    }
}
