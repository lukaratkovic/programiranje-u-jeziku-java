package hr.java.production.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Address {
    String street, houseNumber, city, postalCode;
    private static final Logger logger = LoggerFactory.getLogger(Address.class);

    public Address() {
        logger.info("Object of class Address was created.");
    }

    public Address(String street, String houseNumber, String city, String postalCode) {
        logger.info("Object of class Address was created.");
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

    public static class Builder {
        private String street, houseNumber, city, postalCode;

        public Builder() {
        }

        public Builder withStreet(String street) {
            this.street = street;
            return this;
        }

        public Builder withHouseNumber(String houseNumber) {
            this.houseNumber = houseNumber;
            return this;
        }

        public Builder withCity(String city) {
            this.city = city;
            return this;
        }

        public Builder withPostalCode(String postalCode) {
            this.postalCode = postalCode;
            return this;
        }

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