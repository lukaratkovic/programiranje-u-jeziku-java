package hr.java.production.model;

public class Address {
    String street, houseNumber, city, postalCode;

    public Address(String street, String houseNumber, String city, String postalCode) {
        if(street == "") street="Vrbik";
        if(houseNumber == "") houseNumber="8";
        if(city == "") city="Zagreb";
        if(postalCode == "") postalCode="10000";
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
}