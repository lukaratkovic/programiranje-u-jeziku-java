package hr.java.production.genericsi;

import hr.java.production.model.Item;
import hr.java.production.model.Store;
import hr.java.production.model.Technical;

import java.util.List;
import java.util.Set;

public class TechnicalStore<T extends Technical> extends Store {
    List<T> lista;

    public TechnicalStore(String name, String webAddress, Set<Item> items, List<T> lista) {
        super(name, webAddress, items);
        this.lista = lista;
    }
}
