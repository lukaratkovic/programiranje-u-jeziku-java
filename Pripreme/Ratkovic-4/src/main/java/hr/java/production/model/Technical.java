package hr.java.production.model;

/**
 * Contains method for retrieval of years of warranty for a laptop
 */
public sealed interface Technical permits Laptop {
    /**
     * Blueprint for retrieval of years of warranty for a laptop
     *
     * @return years of warranty
     */
    int getWarranty();
}
