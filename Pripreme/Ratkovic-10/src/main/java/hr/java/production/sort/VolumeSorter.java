package hr.java.production.sort;

import hr.java.production.model.Edible;
import hr.java.production.model.Item;

import java.util.Comparator;

/**
 * Comparator of objects of Item type by volume
 */
public class VolumeSorter<T> implements Comparator<T> {

    /**
     * Default constructor
     */
    public VolumeSorter() {
    }

    /**
     * Compare two objects of type Item by volume
     *
     * @param o1 First item object
     * @param o2 Second item object
     * @return 1, 0, or -1
     */
    @Override
    public int compare(T o1, T o2) {
        return Integer.compare(((Item) o1).getVolume().compareTo(((Item) o2).getVolume()), 0);
    }
}
