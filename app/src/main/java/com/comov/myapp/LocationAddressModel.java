package com.comov.myapp;

public class LocationAddressModel {
    private String address;
    private String city;
    private String state;
    private String country;
    private String postalCode;
    private String knownName;

    public LocationAddressModel(String address, String city, String state, String country, String postalCode, String knownName) {
        this.address = address;
        this.city = city;
        this.state = state;
        this.country = country;
        this.postalCode = postalCode;
        this.knownName = knownName;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getCountry() {
        return country;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getKnownName() {
        return knownName;
    }

    public String getAddress() {
        return address;
    }

    @Override
    public String toString() {
        return "LocationAddressModel{" +
                "address='" + address + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", country='" + country + '\'' +
                ", postalCode='" + postalCode + '\'' +
                ", knownName='" + knownName + '\'' +
                '}';
    }
}
