package hr.java.production.sort;

import hr.java.production.model.Item;

import java.util.Comparator;

public class ProductionSorter implements Comparator<Item> {
    boolean ascending = true;

    public ProductionSorter() {
    }

    public ProductionSorter(boolean ascending) {
        this.ascending = ascending;
    }

    @Override
    public int compare(Item o1, Item o2) {
        if (o1.getSellingPrice().compareTo(o2.getSellingPrice()) > 0) {
            if (ascending) return 1;
            else return -1;
        } else {
            if (ascending) return -1;
            else return 1;
        }
    }

}
