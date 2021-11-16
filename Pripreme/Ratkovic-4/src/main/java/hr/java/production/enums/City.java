package hr.java.production.enums;

public enum City {
    ZAGREB("Zagreb", "10000"),
    DUGO_SELO("Dugo Selo", "10370"),
    RIJEKA("Rijeka", "51000");

    private String postalCode;
    private String name;

    City(String name, String postalCode) {
        this.name = name;
        this.postalCode = postalCode;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getName() {
        return name;
    }
}
