package hr.java.production.genericsi;

import hr.java.production.model.Item;
import hr.java.production.model.Store;
import hr.java.production.model.Technical;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TechnicalStore<T extends Technical> extends Store {
    List<T> itemList;

    public TechnicalStore(String name, String webAddress, List<T> itemList) {
        super(name, webAddress, new HashSet<>());
        this.itemList = itemList;
        Set<Item> set = new HashSet<>();
        for (T i : itemList) {
            set.add((Item) i);
        }
        this.setItems(set);
    }

    public List<T> getItemList() {
        return itemList;
    }

    public void setItemList(List<T> itemList) {
        this.itemList = itemList;
    }
}
