package hr.java.production.sort;

import hr.java.production.model.Item;

import java.util.Comparator;

/**
 * Comparator for objects of Item type by price
 */
public class ProductionSorter implements Comparator<Item> {
    boolean ascending = true;

    /**
     * Default constructor
     */
    public ProductionSorter() {
    }

    /**
     * Constructor that can set ascending or descending sort option
     *
     * @param ascending boolean representing ascending (true) / descending (false) sorting
     */
    public ProductionSorter(boolean ascending) {
        this.ascending = ascending;
    }

    /**
     * Compare two objects of type Item by price
     *
     * @param o1 First item object
     * @param o2 Second item object
     * @return depends on o1, o2, and ascending/descending parameter (see constructor)
     */
    @Override
    public int compare(Item o1, Item o2) {
        int returnValue = 0;
        if (o1.getSellingPrice().compareTo(o2.getSellingPrice()) > 0)
            returnValue = 1;
        else
            returnValue = -1;
        if (!ascending) returnValue *= -1;
        return returnValue;
    }

}
