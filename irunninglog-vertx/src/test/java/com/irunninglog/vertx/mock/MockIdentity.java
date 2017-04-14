package com.irunninglog.vertx.mock;

import com.irunninglog.api.identity.IIdentity;

public class MockIdentity implements IIdentity {

    private String username;
    private long id;
    private boolean created;

    @Override
    public String getUsername() {
        return username;
    }

    public MockIdentity setUsername(String username) {
        this.username = username;
        return this;
    }

    @Override
    public long getId() {
        return id;
    }

    public MockIdentity setId(long id) {
        this.id = id;
        return this;
    }

    @Override
    public boolean isCreated() {
        return created;
    }

    public MockIdentity setCreated(boolean created) {
        this.created = created;
        return this;
    }

}
