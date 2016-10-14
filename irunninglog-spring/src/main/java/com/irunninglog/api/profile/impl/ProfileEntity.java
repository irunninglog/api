package com.irunninglog.api.profile.impl;

import javax.persistence.*;

@Entity
@Table(name = "PROFILE", uniqueConstraints = @UniqueConstraint(columnNames = "EMAIL"))
public class ProfileEntity {

    @Id
    @Column(name="ID")
    private long id;

    @Column(name="EMAIL", nullable = false)
    private String email;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
