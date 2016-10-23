package com.irunninglog.api.profile;

import com.google.common.base.MoreObjects;

public final class Profile {

    private long id;
    private String email;
    private String firstName;
    private String lastName;

    public long getId() {
        return id;
    }

    public Profile setId(long id) {
        this.id = id;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public Profile setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public Profile setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public Profile setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("email", email)
                .add("firstName", firstName)
                .add("lastName", lastName)
                .toString();
    }

}
