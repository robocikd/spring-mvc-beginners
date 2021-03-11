package com.packt.webstore.domain;

public class Customer {
    private long customerId;
    private String name;
    private String address;
    private long noOfOrdersMade;

    public Customer() {
        super();
    }

    public Customer(long customerId, String name, String address) {
        this.customerId = customerId;
        this.name = name;
        this.address = address;
        this.noOfOrdersMade = 0;
    }

    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public long getNoOfOrdersMade() {
        return noOfOrdersMade;
    }

    public void setNoOfOrdersMade(long noOfOrdersMade) {
        this.noOfOrdersMade = noOfOrdersMade;
    }

}
