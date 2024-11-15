package com.lms.models;

public class Address {
	
	// Step 1: Define the attributes
	private Long addressId;
    private String street;
    private String city;
    private String state;
    private String pinCode;
    private String country;

    // Step 2: Design the constructors
    public Address(String street, String city, String state, String pinCode, String country) {
    	this.setStreet(street);
    	this.setCity(city);
    	this.setState(state);
    	this.setPinCode(pinCode);
    	this.setCountry(country);
    }

    // Design the getters and setters and add proper validations
    public Long getAddressId() {
        return addressId;
    }

    public void setAdressId(Long addressId) {
        if (addressId == null || addressId < 0) {
            throw new IllegalArgumentException("Address id cannot be empty!");
        }
        this.addressId = addressId;
    }
    
    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        if (street == null || street.trim().isEmpty()) {
            throw new IllegalArgumentException("Street address cannot be empty!");
        }
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        if (city == null || city.trim().isEmpty()) {
            throw new IllegalArgumentException("City cannot be empty!");
        }
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        if (state == null || state.trim().isEmpty()) {
            throw new IllegalArgumentException("State cannot be empty!");
        }
        this.state = state;
    }

    public String getPinCode() {
        return pinCode;
    }

    public void setPinCode(String pinCode) {
        if (pinCode == null || pinCode.trim().isEmpty() || !pinCode.matches("\\d{5}(-\\d{4})?")) {
            throw new IllegalArgumentException("Invalid zip code format!");
        }
        this.pinCode = pinCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        if (country == null || country.trim().isEmpty()) {
            throw new IllegalArgumentException("Country cannot be empty!");
        }
        this.country = country;
    }
}
