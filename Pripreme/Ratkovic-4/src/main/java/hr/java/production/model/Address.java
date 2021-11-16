package hr.java.production.model;


/**
 * Represents a physical location
 */
public class Address {
    String street, houseNumber, city, postalCode;

    /**
     * Empty constructor for Address class, should be used in conjunction with Build Pattern
     */
    public Address() {
    }

    /**
     * Constructor for Address
     *
     * @param street      Name of street
     * @param houseNumber House number
     * @param city        Name of city
     * @param postalCode  Postal code of city
     */
    public Address(String street, String houseNumber, String city, String postalCode) {
        if (street == "") street = "Vrbik";
        if (houseNumber == "") houseNumber = "8";
        if (city == "") city = "Zagreb";
        if (postalCode == "") postalCode = "10000";
        this.street = street;
        this.houseNumber = houseNumber;
        this.city = city;
        this.postalCode = postalCode;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    /**
     * Build pattern for Address class
     */
    public static class Builder {
        private String street, houseNumber, city, postalCode;

        /**
         * Constructor for Builder class (Address), should be used in conjunction with .build()
         */
        public Builder() {
        }

        /**
         * Build pattern argument for Address Build Pattern
         *
         * @param street Name of street
         * @return Builder
         */
        public Builder withStreet(String street) {
            this.street = street;
            return this;
        }

        /**
         * Build pattern argument for Address Build Pattern
         *
         * @param houseNumber House number
         * @return Builder
         */
        public Builder withHouseNumber(String houseNumber) {
            this.houseNumber = houseNumber;
            return this;
        }

        /**
         * Build pattern argument for Address Build Pattern
         *
         * @param city Name of city
         * @return Builder
         */
        public Builder withCity(String city) {
            this.city = city;
            return this;
        }

        /**
         * Build pattern argument for Address Build Pattern
         *
         * @param postalCode Postal code of city
         * @return Builder
         */
        public Builder withPostalCode(String postalCode) {
            this.postalCode = postalCode;
            return this;
        }

        /**
         * Build method for Address Build Pattern
         *
         * @return Address
         */
        public Address build() {
            Address address = new Address();
            address.street = this.street;
            address.houseNumber = this.houseNumber;
            address.city = this.city;
            address.postalCode = this.postalCode;

            return address;
        }
    }
}