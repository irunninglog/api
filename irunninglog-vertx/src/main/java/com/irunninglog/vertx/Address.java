package com.irunninglog.vertx;

import com.google.common.base.MoreObjects;

public enum Address {

    ProfileGet("com.irunninglog.profile.get");

    private final String address;

    Address(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("address", address)
                .toString();
    }

}
