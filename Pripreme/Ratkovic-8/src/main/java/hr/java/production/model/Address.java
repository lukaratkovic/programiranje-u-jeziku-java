package hr.java.production.model;


import hr.java.production.enums.City;

import java.io.Serializable;
import java.util.Objects;

/**
 * Represents a physical location
 */
public class Address implements Serializable {
    String street, houseNumber;
    City city;
    Long id;

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
     * @param city        enum of city
     */
    public Address(String street, String houseNumber, City city) {
        if (street == "") street = "Vrbik";
        if (houseNumber == "") houseNumber = "8";
        this.street = street;
        this.houseNumber = houseNumber;
        this.city = city;
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

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Address)) return false;
        Address address = (Address) o;
        return Objects.equals(street, address.street) && Objects.equals(houseNumber, address.houseNumber) && city == address.city;
    }

    @Override
    public int hashCode() {
        return Objects.hash(street, houseNumber, city);
    }

    /**
     * Build pattern for Address class
     */
    public static class Builder {
        private String street, houseNumber;
        City city;
        Long id;

        /**
         * Constructor for Builder class (Address), should be used in conjunction with .build()
         */
        public Builder() {
        }

        public Builder withId(Long id) {
            this.id = id;
            return this;
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
        public Builder withCity(City city) {
            this.city = city;
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
            address.id = this.id;

            return address;
        }
    }

    @Override
    public String toString() {
        return street + " " + houseNumber + ", "+city.getName();
    }

    public Long getId() {
        return id;
    }
}