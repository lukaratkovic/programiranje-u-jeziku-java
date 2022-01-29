package hr.java.production.enums;

/**
 * Contains information about cities
 */
public enum City {
    DUGO_SELO("Dugo Selo", 10370),
    KOPRIVNICA("Koprivnica", 48000),
    NEW_YORK("New York", 10271),
    RIJEKA("Rijeka", 51000),
    ZAGREB("Zagreb", 10000);

    private Integer postalCode;
    private String name;

    /**
     * Constructor for City
     *
     * @param name       city name
     * @param postalCode city postal code
     */
    City(String name, Integer postalCode) {
        this.name = name;
        this.postalCode = postalCode;
    }

    public Integer getPostalCode() {
        return postalCode;
    }

    public String getName() {
        return name;
    }
}
