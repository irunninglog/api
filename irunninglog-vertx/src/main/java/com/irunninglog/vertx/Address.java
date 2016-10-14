package com.irunninglog.vertx;

public enum Address {

    ProfileGet("com.irunninglog.profile.get");

    private final String address;

    Address(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

}
