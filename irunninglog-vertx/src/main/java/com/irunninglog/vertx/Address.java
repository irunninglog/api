package com.irunninglog.vertx;

public enum Address {

    Profile("com.irunninglog.profile");

    private final String address;

    Address(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

}
