package com.irunninglog.mock;

import com.irunninglog.api.ping.IPing;

public class MockPing implements IPing {

    private String timestamp;

    @Override
    public IPing setTimestamp(String s) {
        this.timestamp = s;
        return this;
    }

    @Override
    public String getTimestamp() {
        return timestamp;
    }

}
