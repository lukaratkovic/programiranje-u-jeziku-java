package hr.java.production.genericsi;

import hr.java.production.model.Edible;
import hr.java.production.model.Item;
import hr.java.production.model.Store;

import java.util.List;
import java.util.Set;

public class FoodStore<T extends Edible> extends Store {
    List<T> lista;

    public FoodStore(String name, String webAddress, Set<Item> items, List<T> lista) {
        super(name, webAddress, items);
        this.lista = lista;
    }
}
