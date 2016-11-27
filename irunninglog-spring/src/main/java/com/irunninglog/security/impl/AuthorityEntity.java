package com.irunninglog.security.impl;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "authority_entity")
class AuthorityEntity {

    @javax.persistence.Id
    @javax.persistence.GeneratedValue(strategy=javax.persistence.GenerationType.TABLE)
    private long id;

    @Column(nullable = false)
    private String name;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
