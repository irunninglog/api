package com.irunninglog.api.profile.impl;

import javax.persistence.*;

@Entity
@Table(name = "PROFILE")
class ProfileEntity {

    @Id
    @Column(name="ID")
    @javax.persistence.GeneratedValue(strategy=javax.persistence.GenerationType.TABLE)
    private long id;

    @Column(name="EMAIL", nullable = false, unique = true)
    private String email;

    @Column(name="PASSWORD", nullable = false)
    private String password;

    @Column(name = "FIRSTNAME", nullable = false)
    private String firstName;

    @Column(name = "LASTNAME", nullable = false)
    private String lastName;

    long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

}
